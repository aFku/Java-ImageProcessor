import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { KeycloakAngularModule, KeycloakService } from 'keycloak-angular';

const keycloakService = new KeycloakService();

@NgModule({
  declarations: [
  ],
  imports: [
    BrowserModule,
    KeycloakAngularModule
  ],
  providers: [
    {
      provide: KeycloakService,
      useValue: keycloakService
    }
  ],
  bootstrap: []
})

export class AppModule {
  constructor() {
    keycloakService.init({
      config: {
        url: 'http://localhost:8080/',
        realm: 'test-realm',
        clientId: 'test-client'
      },
      initOptions: {
        onLoad: 'login-required'
      },
      enableBearerInterceptor: true,
      bearerExcludedUrls: ['/assets']
    });
  }
}