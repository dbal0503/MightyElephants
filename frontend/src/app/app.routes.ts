import { Routes } from '@angular/router';
import { HomeComponent } from '../app/home/home.component';
import { PaymentComponent } from '../app/payment/payment.component';
import {QuoteRequestComponent} from "./quote/quote-request.component";
import { LiveTrackingComponent } from './live-tracking/live-tracking.component';

export const routes: Routes = [
    { path: '', component: HomeComponent },
    { path: 'live-tracking', component: LiveTrackingComponent },
    { path: 'payment', component: PaymentComponent },
    { path: 'quote-request', component: QuoteRequestComponent},
];
