import { Component, OnInit } from '@angular/core';
import {
  FormBuilder,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import {
  HttpClient,
  HttpClientModule,
  HttpHeaders,
} from '@angular/common/http';
import { OfficeLocation } from './interfaces/IOfficeLocation';

declare var google: any;
@Component({
  selector: 'app-quote-request',
  standalone: true,
  templateUrl: './quote-request.component.html',
  styleUrls: ['./quote-request.component.scss'],
  imports: [CommonModule, ReactiveFormsModule, HttpClientModule],
})
export class QuoteRequestComponent implements OnInit {
  quoteForm: FormGroup;
  volumetricDivisor: number = 5000;
  shippingOptions: any[] = [];
  selectedOption: any;
  isSelected: boolean = false;
  originCoords: { lat: number; lng: number } | null = null;
  destinationCoords: { lat: number; lng: number } | null = null;
  distance: number | null = null;
  origin: any;
  destination: any;
  isPaying: boolean = false;

  constructor(
    private fb: FormBuilder,
    private router: Router,
    private http: HttpClient
  ) {
    this.quoteForm = this.fb.group({
      senderName: ['', [Validators.required]],
      recipientName: ['', [Validators.required]],
      origin: [{ value: '', disabled: true }, [Validators.required]],
      destination: ['', [Validators.required]],
      length: ['', [Validators.required, Validators.min(0)]],
      width: ['', [Validators.required, Validators.min(0)]],
      height: ['', [Validators.required, Validators.min(0)]],
      weight: [
        { value: '', disabled: true },
        [Validators.required, Validators.min(0)],
      ],
    });
  }

  ngOnInit(): void {
    this.initAutocomplete('origin');
    this.initAutocomplete('destination');
    this.quoteForm.valueChanges.subscribe(() => this.updateWeight());
  }

  // Place findNearestDropOff method here
  findNearestDropOff(field: 'nearestDropOff'): void {
    const input = document.getElementById(field) as HTMLInputElement;
    const autocomplete = new google.maps.places.Autocomplete(input, {
      types: ['geocode'],
    });

    autocomplete.addListener('place_changed', () => {
      const place = autocomplete.getPlace();
      if (place.geometry) {
        const userCoords = {
          lat: place.geometry.location.lat(),
          lng: place.geometry.location.lng(),
        };

        this.getNearestOffice(userCoords);
      }
    });
  }

  // The getNearestOffice method should also go here
  getNearestOffice(coords: { lat: number; lng: number }): void {
    const token = localStorage.getItem('id_token');

    if (!token) {
      console.error('Token is missing');
      this.isPaying = false;
      return; // You can handle this case with an alert or redirection if needed
    }
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);

    this.http
      .get<OfficeLocation>(
        `http://localhost:8080/api/office-locations/nearest?lat=${coords.lat}&lon=${coords.lng}`,
        { headers }
      )
      .subscribe((office: { address: string; lat: number; lng: number }) => {
        this.originCoords = { lat: office.lat, lng: office.lng };
        this.origin = office.address;
        this.quoteForm.patchValue({ origin: this.origin });
        this.quoteForm.get('origin')?.disable();
        console.log('Nearest office set as origin:', this.origin);
      });
  }

  initAutocomplete(field: 'origin' | 'destination'): void {
    const input = document.getElementById(field) as HTMLInputElement;
    const autocomplete = new google.maps.places.Autocomplete(input, {
      types: ['geocode'],
    });

    autocomplete.addListener('place_changed', () => {
      const place = autocomplete.getPlace();
      if (place.geometry) {
        const coords = {
          lat: place.geometry.location.lat(),
          lng: place.geometry.location.lng(),
        };

        if (field === 'origin') {
          this.originCoords = coords;
          this.origin = place.formatted_address;
          console.log('Origin: ', this.origin);
          console.log('Origin coordinates:', this.originCoords);
        } else {
          this.destination = place.formatted_address;
          this.destinationCoords = coords;
          console.log('Destination: ', this.destination);
          console.log('Destination coordinates:', this.destinationCoords);
        }

        if (this.originCoords && this.destinationCoords) {
          this.calculateDistance();
        }
      }
    });
  }

  calculateDistance(): void {
    const service = new google.maps.DistanceMatrixService();
    service.getDistanceMatrix(
      {
        origins: [
          new google.maps.LatLng(
            this.originCoords!.lat,
            this.originCoords!.lng
          ),
        ],
        destinations: [
          new google.maps.LatLng(
            this.destinationCoords!.lat,
            this.destinationCoords!.lng
          ),
        ],
        travelMode: google.maps.TravelMode.DRIVING,
      },
      (response: unknown, status: unknown) => {
        if (status === google.maps.DistanceMatrixStatus.OK) {
          const distanceData = response as {
            rows: { elements: { distance: { value: number } }[] }[];
          };
          this.distance =
            distanceData.rows[0].elements[0].distance.value / 1000;
          console.log('Calculated distance:', this.distance, 'km');
        } else {
          console.error('Distance calculation failed:', status);
        }
      }
    );
  }

  updateWeight() {
    const length = this.quoteForm.get('length')?.value;
    const width = this.quoteForm.get('width')?.value;
    const height = this.quoteForm.get('height')?.value;

    if (length && width && height) {
      // Calculate volumetric weight
      const volumetricWeight =
        (length * width * height) / this.volumetricDivisor;
      this.quoteForm.patchValue({
        weight: volumetricWeight,
      });
    }
  }

  submitQuoteRequest(formData: any): void {
    console.log('Request sent to backend:', formData);
  }
  onSubmit() {
    if (this.quoteForm.valid) {
      const weight = this.quoteForm.get('weight')?.value;
      const origin = this.quoteForm.get('origin')?.value;
      console.log('Origin: ', origin);
      const destination = this.quoteForm.get('destination')?.value;
      const distance = this.distance;
      const senderName = this.quoteForm.get('senderName')?.value;
      const recipientName = this.quoteForm.get('recipientName')?.value;

      if (!senderName || !recipientName) {
        alert('Please enter a valid sender and recipient name.');
        return;
      }

      const standardMultiplier = 2.5;
      const expressMultiplier = 5.0;

      let _estimatedDeliveryStandard = '3-5 business days';
      let _estimatedDeliveryExpress = '1-2 business days';
      let distance_cost = 0;

      if (distance != null) {
        if (distance > 20) {
          distance_cost = 10;
        } else if (distance > 50) {
          distance_cost = 12;
        } else if (distance > 100) {
          distance_cost = 15;

          // Idea how we could calculate the estimated delivery time based on the distance and weight REVIEW
          if (weight > 20) {
            _estimatedDeliveryStandard = '8-11 business days';
            _estimatedDeliveryExpress = '4-7 business days';
          } else if (weight > 10) {
            _estimatedDeliveryStandard = '7-10 business days';
            _estimatedDeliveryExpress = '4-6 business days';
          }
        }
      }

      const shippingOptions = [
        {
          price: (weight * standardMultiplier + 11 + distance_cost).toFixed(2),
          shippingType: 'Standard',
          estimatedDelivery: _estimatedDeliveryStandard,
        },
        {
          price: (weight * expressMultiplier + 23 + distance_cost).toFixed(2),
          shippingType: 'Express',
          estimatedDelivery: _estimatedDeliveryExpress,
        },
      ];

      this.shippingOptions = shippingOptions;
      console.log('Shipping options returned: ', this.shippingOptions);
    } else {
      this.markFormGroupTouched(this.quoteForm);
    }
  }

  selectOption(option: any) {
    this.selectedOption = option;
    this.isSelected = true;
  }

  onPay() {
    if (this.isPaying) {
      return;
    }
    this.isPaying = true;
    const token = localStorage.getItem('id_token');

    if (!token) {
      console.error('Token is missing');
      this.isPaying = false;
      return; // You can handle this case with an alert or redirection if needed
    }
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    if (this.selectedOption) {
      const weight = this.quoteForm.get('weight')?.value;
      const origin = this.quoteForm.get('origin')?.value;
      const destination = this.quoteForm.get('destination')?.value;

      const quoteData = {
        origin: this.origin,
        originLat: this.originCoords?.lat,
        originLong: this.originCoords?.lng,
        destination: this.destination,
        destinationLat: this.destinationCoords?.lat,
        destinationLong: this.destinationCoords?.lng,
        price: this.selectedOption.price,
        weight: this.quoteForm.get('weight')?.value,
        shippingType: this.selectedOption.shippingType,
        estimatedDelivery: this.selectedOption.estimatedDelivery,
        sender: this.quoteForm.get('senderName')?.value,
        recipient: this.quoteForm.get('recipientName')?.value,
      };
      console.log(quoteData);

      this.http
        .post('http://localhost:8080/api/quote/save-request', quoteData, {
          headers,
        })
        .subscribe({
          next: (response) => {
            console.log('Quote saved successfully:', response);
            this.router.navigate(['/payment'], {
              queryParams: { quoteId: response },
            });
          },
          error: (error) => {
            console.error('Error saving quote:', error);
            alert('Error saving quote. Please try again later.');
            this.isPaying = false;
          },
        });
    } else {
      alert('Please select a shipping option.');
    }
  }

  markFormGroupTouched(formGroup: FormGroup): void {
    Object.values(formGroup.controls).forEach((control) => {
      control.markAsTouched();
      if (control instanceof FormGroup) {
        this.markFormGroupTouched(control);
      }
    });
  }

  isFieldInvalid(fieldName: string): boolean {
    const field = this.quoteForm.get(fieldName);
    return field ? field.invalid && (field.dirty || field.touched) : false;
  }
}
