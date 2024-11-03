// payment.component.ts
import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';

@Component({
  selector: 'app-payment',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './payment.component.html',
  styleUrls: ['./payment.component.scss']
})
export class PaymentComponent implements OnInit {
  paymentForm: FormGroup;
  paymentMethod: 'card' | 'paypal' = 'card';

  constructor(private fb: FormBuilder) {
    this.paymentForm = this.fb.group({
      paymentMethod: ['card'],
      cardNumber: ['', [
        Validators.required,
        Validators.pattern('^[0-9]{16}$')
      ]],
      cardHolder: ['', [
        Validators.required,
        Validators.pattern('^[a-zA-Z ]+$')
      ]],
      expiryDate: ['', [
        Validators.required,
        Validators.pattern('^(0[1-9]|1[0-2])\/([0-9]{2})$')
      ]],
      cvv: ['', [
        Validators.required,
        Validators.pattern('^[0-9]{3,4}$')
      ]],
      email: ['', [
        Validators.email
      ]]
    });
  }

  ngOnInit(): void {
    this.paymentForm.get('paymentMethod')?.valueChanges.subscribe(method => {
      this.paymentMethod = method;
      this.updateValidators();
    });
  }

  updateValidators() {
    const cardFields = ['cardNumber', 'cardHolder', 'expiryDate', 'cvv'];
    const emailField = this.paymentForm.get('email');

    if (this.paymentMethod === 'card') {
      emailField?.clearValidators();
      cardFields.forEach(field => {
        this.paymentForm.get(field)?.setValidators([Validators.required]);
      });
    } else {
      cardFields.forEach(field => {
        this.paymentForm.get(field)?.clearValidators();
      });
      emailField?.setValidators([Validators.required, Validators.email]);
    }

    this.paymentForm.updateValueAndValidity();
  }

  onSubmit() {
    if (this.paymentForm.valid) {
      console.log('Payment form submitted:', this.paymentForm.value);
      //TODO Send data to backend, to be implemented
    } else {
      this.markFormGroupTouched(this.paymentForm);
    }
  }

  markFormGroupTouched(formGroup: FormGroup) {
    Object.values(formGroup.controls).forEach(control => {
      control.markAsTouched();
      if (control instanceof FormGroup) {
        this.markFormGroupTouched(control);
      }
    });
  }

  isFieldInvalid(fieldName: string): boolean {
    const field = this.paymentForm.get(fieldName);
    return field ? field.invalid && (field.dirty || field.touched) : false;
  }
}