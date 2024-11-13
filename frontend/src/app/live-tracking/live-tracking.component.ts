import { Component, AfterViewInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';

// Remove the top-level Leaflet import
// import * as L from 'leaflet'; // Removed

// Import SockJS and StompJS for WebSocket communication
import SockJS from 'sockjs-client';
import { Client } from '@stomp/stompjs';

@Component({
  selector: 'app-live-tracking',
  templateUrl: './live-tracking.component.html',
  styleUrls: ['./live-tracking.component.scss'],
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,        // Import FormsModule for ngModel
    HttpClientModule    // Import HttpClientModule for HttpClient
  ]
})
export class LiveTrackingComponent implements AfterViewInit {
  private map: any;
  private marker: any;
  public trackingNumber: string = '';

  private stompClient!: Client;

  constructor(private http: HttpClient) {}

  ngAfterViewInit(): void {
    this.initMap();
  }

  private async initMap() {
    if (typeof window !== 'undefined') {
      const L = await import('leaflet');

      // Fix icon paths (Leaflet uses images for markers)
      this.fixLeafletIconPaths(L);

      this.map = L.map('map').setView([45.495247, -73.578582], 13);

      L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
        maxZoom: 18,
        minZoom: 3,
        attribution: '&copy; OpenStreetMap contributors'
      }).addTo(this.map);
    }
  }

  private fixLeafletIconPaths(L: any) {
    // Fix for default icon paths
    const iconRetinaUrl = 'assets/marker-icon-2x.png';
    const iconUrl = 'assets/marker-icon.png';
    const shadowUrl = 'assets/marker-shadow.png';
    L.Icon.Default.mergeOptions({
      iconRetinaUrl,
      iconUrl,
      shadowUrl
    });
  }

  public startTracking() {
    if (typeof window !== 'undefined') {
      const payload = {
        trackingNumber: this.trackingNumber,
        startAddress: 'Your Start Address',
        endAddress: 'Your End Address'
      };

      this.http.post('http://localhost:8080/start-tracking', payload).subscribe(() => {
        this.connectWebSocket();
      });
      
    }
  }

  private connectWebSocket() {
    if (typeof window !== 'undefined') {
      const socket = new SockJS('http://localhost:8080/ws');
      this.stompClient = new Client({
        webSocketFactory: () => socket,
        debug: (str) => { console.log(str); },
        onConnect: () => {
          this.stompClient.subscribe(`/topic/tracking/${this.trackingNumber}`, (message) => {
            if (message.body) {
              const coord = JSON.parse(message.body);
              this.handleMessage(coord);
            }
          });
        }
      });
      this.stompClient.activate();
    }
  }

  private handleMessage(coord: number[]) {
    if (Array.isArray(coord) && coord.length === 2) {
      const [latitude, longitude] = coord;
      this.updateMarker(latitude, longitude);
    }
  }

  private async updateMarker(lat: number, lng: number) {
    if (typeof window !== 'undefined') {
      const L = await import('leaflet');
      if (this.marker) {
        this.marker.setLatLng([lat, lng]);
      } else {
        this.marker = L.marker([lat, lng]).addTo(this.map);
      }
      this.map.setView([lat, lng], this.map.getZoom());
    }
  }
}
