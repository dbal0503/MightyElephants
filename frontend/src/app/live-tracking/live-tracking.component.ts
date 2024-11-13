import { Component, AfterViewInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';

import SockJS from 'sockjs-client';
import { Client } from '@stomp/stompjs';

@Component({
  selector: 'app-live-tracking',
  templateUrl: './live-tracking.component.html',
  styleUrls: ['./live-tracking.component.scss'],
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,      
    HttpClientModule 
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

    const iconUrl = 'frontend\public\assets\marker.png';
    L.Icon.Default.mergeOptions({
      iconUrl,
    });
  }

  public startTracking() {
    if (typeof window !== 'undefined') {
      const payload = {
        trackingNumber: this.trackingNumber,
        startAddress: '1555 Rene-Levesque Blvd W, Montreal, QC H3G 0G9',
        endAddress: '790 William St, Montreal, QC H3C 0Y4'
      };
  
      const token = localStorage.getItem('id_token');
      const headers = { 'Authorization': 'Bearer ' + token };
  
      this.http.post('http://localhost:8080/start-tracking', payload, { headers }).subscribe((response: any) => {
        this.connectWebSocket();
        const routeCoordinates = response.routeCoordinates;
        this.drawRoute(routeCoordinates);
      });
    }
  }
  private async drawRoute(routeCoordinates: number[][]) {
    if (typeof window !== 'undefined') {
      const L = await import('leaflet');
  
      const latLngs: [number, number][] = routeCoordinates.map(coord => [coord[0], coord[1]] as [number, number]); // [latitude, longitude]
  
      L.polyline(latLngs, { color: 'blue' }).addTo(this.map);
    }
  }
    

  private connectWebSocket() {
    if (typeof window !== 'undefined') {
      const token = localStorage.getItem('id_token');
      //add token to header of sockjs
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
        const myIcon = L.icon({
          iconUrl: 'assets/marker.png',
          iconRetinaUrl: 'assets/marker.png',
          iconSize: [25, 25],
          iconAnchor: [12, 41],
          popupAnchor: [1, -34],
        });
  
        this.marker = L.marker([lat, lng], { icon: myIcon }).addTo(this.map);
      }
      this.map.setView([lat, lng], this.map.getZoom());
    }
  }
  
}
