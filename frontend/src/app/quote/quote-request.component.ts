import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { HttpClient, HttpClientModule } from '@angular/common/http';


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

  constructor(private fb: FormBuilder, private router: Router,  private http: HttpClient ) {
    this.quoteForm = this.fb.group({
      origin: ['', [Validators.required]],
      destination: ['', [Validators.required]],
      length: ['', [Validators.required, Validators.min(0)]],
      width: ['', [Validators.required, Validators.min(0)]],
      height: ['', [Validators.required, Validators.min(0)]],
      weight: [{ value: '', disabled: true }, [Validators.required, Validators.min(0)]]
    });
  }

  ngOnInit(): void {
    this.quoteForm.valueChanges.subscribe(() => this.updateWeight());
  }
  updateWeight() {
    const length = this.quoteForm.get('length')?.value;
    const width = this.quoteForm.get('width')?.value;
    const height = this.quoteForm.get('height')?.value;

    if (length && width && height) {
      // Calculate volumetric weight
      const volumetricWeight = (length * width * height) / this.volumetricDivisor;
      this.quoteForm.patchValue({
        weight: volumetricWeight
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
      const destination = this.quoteForm.get('destination')?.value;

      const standardMultiplier = 2.5;
      const expressMultiplier = 5.0;

      const shippingOptions = [
        {
          price: (weight * standardMultiplier)+11,
          shippingType: 'Standard',
          estimatedDelivery: '3-5 business days'
        },
        {
          price: (weight * expressMultiplier)+23,
          shippingType: 'Express',
          estimatedDelivery: '1-2 business days'
        }
      ];

      this.shippingOptions = shippingOptions;
      console.log("Shipping options returned: ", this.shippingOptions);
    } else {
      this.markFormGroupTouched(this.quoteForm);
    }
  }



  selectOption(option: any) {
    this.selectedOption = option;
    this.isSelected = true;
  }

  onPay() {
    if (this.selectedOption) {
      const weight = this.quoteForm.get('weight')?.value;
      const origin = this.quoteForm.get('origin')?.value;
      const destination = this.quoteForm.get('destination')?.value;

      const quoteData = {
        origin: origin,
        destination: destination,
        price: this.selectedOption.price,
        weight: weight,
        shippingType: this.selectedOption.shippingType
      };

      this.http.post('http://localhost:8080/api/quote/save-request', quoteData).subscribe(
        (response) => {
          console.log('Quote saved successfully:', response);
          this.router.navigate(['/payment'], {
            state: { selectedQuote: this.selectedOption }
          });
        },
        (error) => {
          console.error('Error saving quote:', error);
          alert('Error saving quote. Please try again later.');
          this.router.navigate(['/payment'], {//Temporary WILL REMOVE
            state: { selectedQuote: this.selectedOption } //Temporary WILL REMOVE
          });//Temporary WILL REMOVE
        }
      );
    } else {
      alert('Please select a shipping option.');
    }
  }

  markFormGroupTouched(formGroup: FormGroup): void {
    Object.values(formGroup.controls).forEach(control => {
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
