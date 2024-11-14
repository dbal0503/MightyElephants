import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, FormControl,ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { HttpClient, HttpHeaders,HttpClientModule } from '@angular/common/http';

@Component({
  selector: 'app-payment',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, HttpClientModule],
  templateUrl: './payment.component.html',
  styleUrls: ['./payment.component.scss']
})
export class PaymentComponent implements OnInit {
  paymentForm: FormGroup;
  paymentMethod: 'card' | 'paypal' = 'card';
  quoteId: string | null = null;
  paymentId: string | null = null;
  isProcessing = false;

  constructor(private fb: FormBuilder,
              private route: ActivatedRoute,
              private router: Router,
              private http: HttpClient) {
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
        Validators.pattern('^[0-9]{3}$')
      ]],
      email: ['', [
        Validators.email
      ]]
    });
  }

  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      const quoteId = params['quoteId'];
      if (quoteId) {
        this.quoteId = quoteId;
        console.log("Quote ID: ", this.quoteId);
      }else {
        console.error("Quote ID not found");
      }
    });
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
      emailField?.updateValueAndValidity();

      cardFields.forEach(field => {
        const control = this.paymentForm.get(field);
        control?.setValidators([Validators.required]);
        control?.updateValueAndValidity();
      });
    } else {
      cardFields.forEach(field => {
        const control = this.paymentForm.get(field);
        control?.clearValidators();
        control?.updateValueAndValidity();
      });

      emailField?.setValidators([Validators.required, Validators.email]);
      emailField?.updateValueAndValidity();
    }
  }
  private markFormGroupTouched(formGroup: FormGroup): void {
    Object.values(formGroup.controls).forEach(control => {
      if (control instanceof FormControl) {
        control.markAsTouched();
      } else if (control instanceof FormGroup) {
        this.markFormGroupTouched(control);
      }
    });
  }

  onSubmit() {
    if (this.paymentForm.valid && this.quoteId) {
      this.isProcessing = true;
      const token = localStorage.getItem('id_token');

      if (!token) {
        alert('Authentication token missing. Please log in again.');
        this.isProcessing = false;
        return;
      }

      const headers = new HttpHeaders()
        .set('Authorization', `Bearer ${token}`)
        .set('Content-Type', 'application/json');

      let paymentData: any;

      if (this.paymentMethod === 'card') {
        const [month, year] = this.paymentForm.get('expiryDate')?.value.split('/') || [];

        paymentData = {
          paymentMethod: 'CREDIT_CARD',
          quoteId: this.quoteId,
          details: {
            cardNumber: this.paymentForm.get('cardNumber')?.value,
            cardHolderName: this.paymentForm.get('cardHolder')?.value,
            expiryMonth: month,
            expiryYear: `20${year}`, // Convert to full year
            cvv: this.paymentForm.get('cvv')?.value
          },
        };
      } else {
        paymentData = {
          paymentMethod: 'PAYPAL',
          quoteId: this.quoteId,
          details: {
            email: this.paymentForm.get('email')?.value
          },
        };
      }
      console.log(paymentData);

      this.http.post('http://localhost:8080/api/payments/process', paymentData, { headers })
        .subscribe({
          next: (response) => {
            console.log("Payment Id: ", response);
            const shippindLabelData = {
              quoteId: this.quoteId,
              paymentId: response
            };
            //temporary to test the endpoint but this will need to called on the shipping label route/page
            this.http.post('http://localhost:8080/api/shippinglabel/create', shippindLabelData, { headers })
              .subscribe({
                next: (response) => {
                  console.log('Shipping label data:', response);
                },
                error: (error) => {
                  console.error('Error saving quote:', error);
                  alert('Error saving quote. Please try again later.');
                  this.isProcessing = false;
                }
              });

            //Needs to route to shipping label page
            /*this.router.navigate(['/payment-success'], {
              queryParams: {
                paymentId: response,
                quoteId: this.quoteId
              }
            });*/
          },
          error: (error) => {
            this.isProcessing = false;
            console.log('Payment processing error:', error);
            alert('Error processing payment. Please try again later.');
          }
        });
    } else {
      this.markFormGroupTouched(this.paymentForm);
    }
  }

  isFieldInvalid(fieldName: string): boolean {
    const field = this.paymentForm.get(fieldName);
    return field ? field.invalid && (field.dirty || field.touched) : false;
  }
  private cleanCreditCardNumber(cardNumber: string): string {
    return cardNumber.replace(/\s+/g, '');
  }
}
