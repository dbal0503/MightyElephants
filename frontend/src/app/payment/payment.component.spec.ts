import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ReactiveFormsModule } from '@angular/forms';
import { PaymentComponent } from './payment.component';

describe('PaymentComponent', () => {
  let component: PaymentComponent;
  let fixture: ComponentFixture<PaymentComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PaymentComponent, ReactiveFormsModule]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PaymentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should initialize with card payment method', () => {
    expect(component.paymentMethod).toBe('card');
  });

  it('should have invalid form initially', () => {
    expect(component.paymentForm.valid).toBeFalsy();
  });

  it('should validate card number format', () => {
    const cardNumberControl = component.paymentForm.get('cardNumber');
    cardNumberControl?.setValue('1234567890123456');
    expect(cardNumberControl?.valid).toBeTruthy();

    cardNumberControl?.setValue('123'); // Too short
    expect(cardNumberControl?.valid).toBeFalsy();
  });
});