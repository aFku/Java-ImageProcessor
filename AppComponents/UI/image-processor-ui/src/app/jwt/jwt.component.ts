import { Component, OnInit } from '@angular/core';
import { KeycloakService } from 'keycloak-angular';

@Component({
  selector: 'app-jwt',
  standalone: true,
  imports: [],
  templateUrl: './jwt.component.html',
  styleUrl: './jwt.component.css'
})
export class JwtComponent implements OnInit {
  jwtToken: string = '';

  constructor(private readonly keycloakService: KeycloakService) { }

  ngOnInit(): void {
    console.log(this.keycloakService.isLoggedIn());
    // this.keycloakService.getToken().then(token => {
      
    // });
  }
}