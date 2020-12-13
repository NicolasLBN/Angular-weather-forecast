import { Component, Input, OnInit } from '@angular/core';
import { ElementService } from '../services/element.service';


@Component({
  selector: 'app-weather-home',
  templateUrl: './weather-home.component.html',
  styleUrls: ['./weather-home.component.css']
})
export class WeatherHomeComponent implements OnInit {
  weather;

  lon: number;
  lat: number;

  lon2: number;
  lat2: number; 

  zoom: number;
 

  constructor(private elementService: ElementService) { }

  ngOnInit(): void {
    this.getLocation();
    const imageName = this.weather.weather[0].icon;
    const iconSrc = `http://openweathermap.org/img/wn/${imageName}@2x.png`;

  }

  getLocation() {
    if("geolocation" in navigator) {
      navigator.geolocation.watchPosition((success) =>{
        this.lat = success.coords.latitude;
        this.lon = success.coords.longitude;

        this.elementService.getWeatherDataByCoords(this.lat, this.lon).subscribe(data =>{
          this.weather = data;
        });
      });

    }
  }


  getCity(city) {
    this.elementService.getWeatherDataByCityName(city).subscribe((data: any ) => {
      this.weather = data;
      this.lon2 = this.weather.coord.lon; 
      this.lat2 = this.weather.coord.lat; 
    })
  }


  
}
