import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http'
@Injectable({
  providedIn: 'root'
})
export class CitiesService {

  constructor(private http:HttpClient) { }
    getData(){
      let url="http://api.openweathermap.org/data/2.5/weather?q=Budapest&appid=0dcf4ad697f6a1c9f2681e1c327af69f";
      return this.http.get(url);
    }

}
