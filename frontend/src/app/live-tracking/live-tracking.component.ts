import { Component, AfterViewInit } from '@angular/core';

@Component({
  selector: 'app-live-tracking',
  templateUrl: './live-tracking.component.html',
  styleUrls: ['./live-tracking.component.scss']
})
export class LiveTrackingComponent implements AfterViewInit {
  private map: any;

  ngAfterViewInit(): void {
    // Lazy load Leaflet
    import('leaflet').then(L => {
      this.map = L.map('map').setView([39.8282, -98.5795], 4);

      L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
        maxZoom: 18,
        minZoom: 3,
        attribution: '&copy; <a href="http://www.openstreetmap.org/copyright">OpenStreetMap</a>'
      }).addTo(this.map);
    });
  }
}
