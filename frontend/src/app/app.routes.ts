import { Routes } from '@angular/router';
import { HomeComponent } from '../app/home/home.component';
import { PaymentComponent } from '../app/payment/payment.component';

export const routes: Routes = [
    { path: '', component: HomeComponent },
    { path: 'payment', component: PaymentComponent }
];
