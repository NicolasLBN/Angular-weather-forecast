import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { FormGroup, FormControl } from '@angular/forms';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BodyComponent } from './body/body.component';
import { HeaderComponent } from './header/header.component';
import {HttpClientModule} from '@angular/common/http';
import {RouterModule, Routes} from '@angular/router';
import { SigninComponent } from './auth/signin/signin.component';
import { SignupComponent } from './auth/signup/signup.component';
import { AuthService } from './services/auth.service';
import { AuthGuardService } from './services/auth-guard.service';
import { WeatherHomeComponent } from './weather-home/weather-home.component';
import { ForecastComponent } from './forecast/forecast.component';


const appRoutes: Routes = [
  { path: 'auth/signup', component: SignupComponent },
  { path: 'auth/signin', component: SigninComponent },
  { path: 'weatherHome', canActivate: [AuthGuardService], component: WeatherHomeComponent },
  { path: 'Forecast', canActivate: [AuthGuardService], component: ForecastComponent },
  { path: '', redirectTo: 'weatherHome', pathMatch: 'full' },
  { path: '**', redirectTo: 'weatherHome' }
];


@NgModule({
  declarations: [
    AppComponent,
    BodyComponent,
    HeaderComponent,
    SigninComponent,
    SignupComponent,
    WeatherHomeComponent,
    ForecastComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ReactiveFormsModule,
    FormsModule,
    HttpClientModule,
    RouterModule.forRoot(appRoutes)
  ],
  providers: [AuthService, AuthGuardService],
  bootstrap: [AppComponent]
})
export class AppModule {
 }
