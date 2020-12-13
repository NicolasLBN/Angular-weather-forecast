import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import * as moment from 'moment';
import 'rxjs';
import { Observable, throwError } from 'rxjs';





@Injectable({
  providedIn: 'root'
})
export class ElementService {

url = 'https://api.openweathermap.org/data/2.5/weather';
urlforecast = 'http://api.openweathermap.org/data/2.5/forecast';
apiKey = 'ecc4744a7bb0cfd9d6982540cf616ca7';


  constructor(private http: HttpClient) { }



getWeatherDataByCoords(lat, lon){
  let params = new HttpParams()
  .set('lat', lat)
  .set('lon', lon)
  .set('units', 'metric')
  .set('appid', this.apiKey)
  return this.http.get(this.url, { params });

}

getWeatherDataByCityName(city: string)
{
  let params = new HttpParams()
  .set('q', city)
  .set('units', 'metric')
  .set('appid', this.apiKey)

  return this.http.get(this.url, { params });
}

getWeatherForecastDataByCityName(city: string): Observable<any> {
{
  let params = new HttpParams()
  .set('q', city)
  .set('units', 'metric')
  .set('appid', this.apiKey)

  return this.http.get(this.urlforecast, { params });
}


}







}
