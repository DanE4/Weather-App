import { enableProdMode } from '@angular/core';
import { platformBrowserDynamic } from '@angular/platform-browser-dynamic';

import { AppModule } from './app/app.module';
import { environment } from './environments/environment';

if (environment.production) {
  enableProdMode();
}
let NamefromInput;
let url="https://api.openweathermap.org/data/2.5/weather?q={{NamefromInput}}&appid=0dcf4ad697f6a1c9f2681e1c327af69f"

platformBrowserDynamic().bootstrapModule(AppModule)
  .catch(err => console.error(err));
//https://api.openweathermap.org/data/2.5/weather?q=London&appid=0dcf4ad697f6a1c9f2681e1c327af69f
