import { Routes } from '@angular/router';
import { HomeComponent } from '../app/home/home.component';
import { PaymentComponent } from '../app/payment/payment.component';
import {QuoteRequestComponent} from "./quote/quote-request.component";

export const routes: Routes = [
    { path: '', component: HomeComponent },
    { path: 'payment', component: PaymentComponent },
    { path: 'quote-request', component: QuoteRequestComponent}
];
