import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { ElementService } from '../services/element.service';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Forecast } from '../forecast';




@Component({
  // tslint:disable-next-line: component-selector
  selector: 'wp-forecast',
  templateUrl: './forecast.component.html',
  styleUrls: ['./forecast.component.scss']
})
export class ForecastComponent  {
  JSON: any;
  title = 'angular-weather';
  weatherForm: FormGroup;
  forecastForm: FormGroup;
  submitted: boolean = false;
  forecast: any;
  forecast5Days: any;
  showTempMax: boolean = true;
  showTempMean: boolean = true;
  showTempMin: boolean = true;

  constructor(private weatherSer: ElementService) { 

    this.JSON = JSON;
    this.weatherForm = new FormGroup({
      city: new FormControl('', Validators.required),
    });
    this.forecastForm = new FormGroup({
      showMax: new FormControl(true),
      showMean: new FormControl(true),
      showMin: new FormControl(true),
    });
  }


  get city(): string {
    return this.weatherForm.get('city').value;
  }

  get showMax(): boolean {
    return this.forecastForm.get('showMax').value;
  }

  get showMean(): boolean {
    return this.forecastForm.get('showMean').value;
  }

  get showMin(): boolean {
    return this.forecastForm.get('showMin').value;
  }


  isWeatherFormInputValid(fieldName: string): boolean {
    return !(
      this.weatherForm.controls[fieldName].invalid &&
      (this.weatherForm.controls[fieldName].dirty ||
        this.weatherForm.controls[fieldName].touched)
    );
  }

  getForecast(): void {
    this.weatherSer.getWeatherForecastDataByCityName(this.city).subscribe(
      // TODO data: HttpResponse<ExpectedType>
      (data: any) => {
        console.log('App got data:', data);
        this.forecast = data;
        this.getForecast5Days(data.list);
        this.submitted = false;
      },
      (error: HttpErrorResponse) => {
        if (error.statusText === 'Not Found') {
          alert('City not found.');
        } else {
          console.error('Error getting forecast:', error);
        }
      }
    );
  }


    // Massage API data into 5-day forecast
    getForecast5Days(data: any): void {
      console.log('massaging forecast data:', data);

          // TODO Move this to utils and test it
      const isSameDate = (d1: Date, d2: Date): boolean =>
        d1.getDate() === d2.getDate() && d1.getMonth() === d2.getMonth();

      let now = new Date();

    // Condense 3-hour increments into data for 5 individual days
      this.forecast5Days = data.reduce((accumulator: any[], current: any) => {
        const thisDate = new Date(current.dt_txt);
        const indexWithThisDate = accumulator.findIndex((d) =>
          isSameDate(d.date, thisDate)
      );

        const {
          temp_min: currentTempMin,
          temp_max: currentTempMax,
          temp: currentTemp,
      } = current.main;
      // We also only want weather data for dates in the future
      //   (Excluding weather data for the rest of today)
        if (!isSameDate(now, thisDate)) {
        // If current's date isn't in accumulator
        if (indexWithThisDate === -1) {
          // Add it, with min/max/mean temperatures
          accumulator.push({
            date: thisDate,
            tempMin: currentTempMin,
            tempMax: currentTempMax,
            itemsWithThisDate: 1,
            tempSum: currentTemp,
            tempMean: currentTemp,
          });
        } else {
          // Otherwise, factor in current's info to this date
          const knownWeatherInfo = accumulator[indexWithThisDate];

          knownWeatherInfo.tempMin = Math.min(
            knownWeatherInfo.tempMin,
            currentTempMin
          );
          knownWeatherInfo.tempMax = Math.max(
            knownWeatherInfo.tempMax,
            currentTempMax
          );
          knownWeatherInfo.itemsWithThisDate++;
          knownWeatherInfo.tempSum += currentTemp;
          knownWeatherInfo.tempMean =
            knownWeatherInfo.tempSum / knownWeatherInfo.itemsWithThisDate;
        }
      }

      return accumulator;



    }, []);


}

  // TODO Move this to utils and test it
  formatTemperature(n: number): string {
    return `${n.toFixed(0)}\u00b0 C`;
  }

  onSubmit(): void {
    this.submitted = true;

    if (this.weatherForm.valid) {
      this.getForecast();
    }
  }

}
