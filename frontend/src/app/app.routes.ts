import { Routes } from '@angular/router';
import { HomeComponent } from '../app/home/home.component';
import { LiveTrackingComponent } from './live-tracking/live-tracking.component';

export const routes: Routes = [
    { path: '', component: HomeComponent },
    { path: 'live-tracking', component: LiveTrackingComponent },
];
