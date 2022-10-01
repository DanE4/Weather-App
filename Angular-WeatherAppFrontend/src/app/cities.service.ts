import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http'

export interface  City {
  results:{
    name: string;
    temp: string;
    time: string;
  }
}
@Injectable({
  providedIn: 'root'
})
export class CitiesService {
  constructor (private http:HttpClient) {}
}
