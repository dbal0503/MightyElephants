import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router } from '@angular/router';
import {
  HttpClient,
  HttpHeaders,
  HttpClientModule,
} from '@angular/common/http';
import { ReactiveFormsModule } from '@angular/forms';
import { ShippingLabelResponse } from './interfaces/IShippingLabelResponse';

@Component({
  selector: 'app-shippinglabel',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, HttpClientModule],
  templateUrl: './shippinglabel.component.html',
  styleUrls: ['./shippinglabel.component.scss'],
})
export class ShippingLabelComponent implements OnInit {
  paymentId: string | null = null;
  quoteId: string | null = null;
  shippingLabelDetails: any = null;
  isLoading: boolean = true;
  isError: boolean = false;

  constructor(
    private route: ActivatedRoute,
    private http: HttpClient,
    private router: Router
  ) {}

  ngOnInit(): void {
    //tempDetails:
    this.shippingLabelDetails = {
      trackingNumber: '1234567890',
      origin: 'New York, NY',
      destination: 'Los Angeles, CA',
      weight: 2.5,
      shippingType: 'Express',
      price: 19.99,
      estimatedDelivery: new Date('2024-11-20'),
      dateIssued: new Date('2024-11-14'),
    };

    this.route.queryParams.subscribe((params) => {
      this.paymentId = params['paymentId'];
      this.quoteId = params['quoteId'];

      if (!this.paymentId || !this.quoteId) {
        console.error(
          'Required parameters (paymentId or quoteId) are missing.'
        );
        this.isError = true;
        this.isLoading = false;
        return;
      }
      this.createShippingLabel();
    });
  }

  createShippingLabel(): void {
    const token = localStorage.getItem('id_token');

    if (!token) {
      alert('Authentication token missing. Please log in again.');
      this.isLoading = false;
      return;
    }

    const headers = new HttpHeaders()
      .set('Authorization', `Bearer ${token}`)
      .set('Content-Type', 'application/json');

    const shippingLabelData = {
      paymentId: this.paymentId,
      quoteId: this.quoteId,
    };

    this.http
      .post<ShippingLabelResponse>(
        'http://localhost:8080/api/shippinglabel/create',
        shippingLabelData,
        { headers }
      )
      .subscribe({
        next: (response) => {
          this.shippingLabelDetails = response;
          this.isLoading = false;
          const deliveryData = {
            shippingLabelId: response['shippingLabelId'],
            sender: response['sender'],
            shippingType: response['shippingType'],
            origin: response['origin'],
            destination: response['destination'],
          };

          this.http
            .post('http://localhost:8080/api/deliveries/create', deliveryData, {
              headers,
            })
            .subscribe({
              next: (deliveryResponse) => {
                console.log('Delivery created:', deliveryResponse);
              },
              error: (error) => {
                console.error('Error creating delivery:', error);
                alert('Error creating delivery. Please try again later.');
                this.isError = true;
              },
            });
        },
        error: (error) => {
          console.error('Error creating shipping label:', error);
          this.isError = true;
          this.isLoading = false;
        },
      });
  }
}
