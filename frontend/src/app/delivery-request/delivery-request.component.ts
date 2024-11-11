import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { HttpClient, HttpClientModule } from '@angular/common/http';

@Component({
  selector: 'app-delivery-request',
  standalone: true,
  templateUrl: './delivery-request.component.html',
  styleUrls: ['./delivery-request.component.scss'],
  imports: [CommonModule, ReactiveFormsModule, HttpClientModule],
})
export class DeliveryRequestComponent implements OnInit {
  deliveryForm: FormGroup;
  showConfirmation: boolean = false;

  constructor(private fb: FormBuilder, private router: Router, private http: HttpClient) {
    this.deliveryForm = this.fb.group({
      pickup: ['', [Validators.required]],
      dropOff: ['', [Validators.required]],
      specialInstructions: [''],
      deliveryType: ['standard', [Validators.required]]
    });
  }

  ngOnInit(): void {}

  onSubmit() {
    if (this.deliveryForm.valid) {
      console.log('Delivery request submitted:', this.deliveryForm.value);

      // Show the confirmation message
      this.showConfirmation = true;
    } else {
      this.markFormGroupTouched(this.deliveryForm);
    }
  }

  navigateToQuote() {
    const origin = this.deliveryForm.get('pickup')?.value;
    const destination = this.deliveryForm.get('dropOff')?.value;
  
    this.router.navigate(['/quote-request'], {
      state: { origin, destination }
    });
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
