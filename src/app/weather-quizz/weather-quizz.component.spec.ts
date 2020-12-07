import { ComponentFixture, TestBed } from '@angular/core/testing';

import { WeatherQuizzComponent } from './weather-quizz.component';

describe('WeatherQuizzComponent', () => {
  let component: WeatherQuizzComponent;
  let fixture: ComponentFixture<WeatherQuizzComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ WeatherQuizzComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(WeatherQuizzComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
