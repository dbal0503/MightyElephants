import { enableProdMode } from '@angular/core';
import { bootstrapApplication } from '@angular/platform-browser';
import { provideRouter, Routes } from '@angular/router';
import { AppComponent } from './app/app.component';
import { routes } from './app/app.routes';
import { provideHttpClient } from '@angular/common/http';

(window as any).global = window;

bootstrapApplication(AppComponent, {
  providers: [provideRouter(routes),
    provideHttpClient(),
  ],
}).catch((err) => console.error(err));
