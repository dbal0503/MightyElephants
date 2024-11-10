import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';


@Component({
  selector: 'app-quote-request',
  standalone: true,
  templateUrl: './quote-request.component.html',
  styleUrls: ['./quote-request.component.scss'],
  imports: [CommonModule, ReactiveFormsModule],
})
export class QuoteRequestComponent implements OnInit {
  quoteForm: FormGroup;
  volumetricDivisor: number = 5000;

  constructor(private fb: FormBuilder, private router: Router) {
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
  onSubmit(): void {
    if (this.quoteForm.valid) {
      console.log('Quote request submitted:', this.quoteForm.value);

      this.submitQuoteRequest(this.quoteForm.value);
    } else {
      this.markFormGroupTouched(this.quoteForm); // Highlight invalid fields
    }
  }

  submitQuoteRequest(formData: any): void {

    console.log('Request sent to backend:', formData);
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
