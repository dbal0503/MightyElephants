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
      const quoteRequest = this.quoteForm.value;
      console.log('Quote request submitted:', this.quoteForm.value);

      this.http.post<any[]>('/api/options', quoteRequest).subscribe(
          (options) => {
            this.shippingOptions = options;
            console.log('Shipping options:', this.shippingOptions);
          },
          (error) => {
            console.error('Error fetching shipping options:', error);
            alert('Error fetching shipping options. Please try again later.');
          }
        );
    } else {
      this.markFormGroupTouched(this.quoteForm);
    }
  }

  selectOption(option: any) {
    //console.log('Selected shipping option:', option);
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
