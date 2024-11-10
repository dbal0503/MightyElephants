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

  constructor(private fb: FormBuilder, private router: Router) {
    // Initialize the form group
    this.quoteForm = this.fb.group({
      origin: ['', [Validators.required, Validators.minLength(3)]],
      destination: ['', [Validators.required, Validators.minLength(3)]],
      weight: ['', [Validators.required, Validators.min(0.1)]],
      paymentMethod: ['card', Validators.required],  // Default to 'card'
    });
  }

  ngOnInit(): void {}
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
