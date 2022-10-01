import { Component } from '@angular/core';
import {CitiesService}from './cities.service';
@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'Angular-WeatherAppFrontend';
  Temp="";
  Time="";
  result:any;
  constructor(private city:CitiesService){
    this.city.getData().subscribe((result)=>{
      console.warn(result);
      this.result=result;
    })
  }



  getTemp(val: any){
    console.warn(val);
    this.Temp=val;
  }
  getCitieslist(val: any) {
    console.warn(val);
    this.city.getData().subscribe((result)=>{
      console.warn(result);
    })
  }
  getTime() {
    return this.city.getData();
  }

  getProperties() {
    return this.city.getData();
  }
}
