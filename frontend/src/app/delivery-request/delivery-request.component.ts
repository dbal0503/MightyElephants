import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { HttpClient, HttpClientModule } from '@angular/common/http';

@Component({
  selector: 'app-delivery-request',
  standalone: true,
  templateUrl: './delivery-request.component.html',
  styleUrls: ['./delivery-request.component.scss'],
  imports: [CommonModule, ReactiveFormsModule, HttpClientModule], // Ensure ReactiveFormsModule is imported here
})
export class DeliveryRequestComponent implements OnInit {
  deliveryForm: FormGroup;
  showConfirmation: boolean = false;

  constructor(private fb: FormBuilder, private router: Router, private http: HttpClient) {
    this.deliveryForm = this.fb.group({
      pickup: ['', [Validators.required]],
      dropOff: ['', [Validators.required]],
      signedIn: [false, [Validators.required]],
      name: [''],
      email: ['', [Validators.email]],
      specialInstructions: [''],
      deliveryType: ['standard', [Validators.required]]
    });
  }

  ngOnInit(): void {
    // Set validators for name and email if the user is not signed in
    this.deliveryForm.get('signedIn')?.valueChanges.subscribe((signedIn) => {
      if (!signedIn) {
        this.deliveryForm.get('name')?.setValidators([Validators.required]);
        this.deliveryForm.get('email')?.setValidators([Validators.required, Validators.email]);
      } else {
        this.deliveryForm.get('name')?.clearValidators();
        this.deliveryForm.get('email')?.clearValidators();
      }
      this.deliveryForm.get('name')?.updateValueAndValidity();
      this.deliveryForm.get('email')?.updateValueAndValidity();
    });
  }

  onSignedInChange() {
    const signedIn = this.deliveryForm.get('signedIn')?.value;
    if (!signedIn) {
      this.deliveryForm.get('name')?.setValidators([Validators.required]);
      this.deliveryForm.get('email')?.setValidators([Validators.required, Validators.email]);
    } else {
      this.deliveryForm.get('name')?.clearValidators();
      this.deliveryForm.get('email')?.clearValidators();
    }
    this.deliveryForm.get('name')?.updateValueAndValidity();
    this.deliveryForm.get('email')?.updateValueAndValidity();
  }

  onSubmit() {
    if (this.deliveryForm.valid) {
      console.log('Delivery request submitted:', this.deliveryForm.value);
      this.showConfirmation = true;
    } else {
      this.markFormGroupTouched(this.deliveryForm);
    }
  }

  navigateToQuote() {
    this.router.navigate(['/quote-request']);
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
    const field = this.deliveryForm.get(fieldName);
    return field ? field.invalid && (field.dirty || field.touched) : false;
  }
}
