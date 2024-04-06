import { Component } from '@angular/core';
import { KeycloakService } from 'keycloak-angular';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'image-processor-frontend';

  authenticated = false;

  constructor(private readonly keycloak: KeycloakService) {
    this.keycloak.isLoggedIn().then((authenticated) => {
      this.authenticated = authenticated;
      if (authenticated) {
        const roles = this.keycloak.getUserRoles();
      }
    });
  }

  login() {
    this.keycloak.login();
  }

  logout() {
    this.keycloak.logout();
  }
}