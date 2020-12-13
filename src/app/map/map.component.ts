import { Component, AfterViewInit, Input } from '@angular/core';
import * as L from 'leaflet';


@Component({
  selector: 'app-map',
  templateUrl: './map.component.html',
  styleUrls: ['./map.component.css']
})
export class MapComponent implements AfterViewInit {

  map;
  @Input() lon2: any 
  @Input() lat2: any

  constructor() { }

  ngAfterViewInit(): void {
    this.createMap();
  }
  createMap() {
    const place = {
      lati: this.lat2,
      long: this.lon2,
    };

    const zoomLevel = 12;
    this.map = L.map('map', {
      center: [place.lati, place.long],
      zoom: zoomLevel
    });

    const mainLayer = L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
      minZoom: 12,
      maxZoom: 17,
      attribution: '&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
    });

    mainLayer.addTo(this.map);
  }

}
