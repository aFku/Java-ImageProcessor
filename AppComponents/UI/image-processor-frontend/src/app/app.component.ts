import { Component } from '@angular/core';
import { KeycloakService } from 'keycloak-angular';
import { HttpClient } from '@angular/common/http';
import { UploadService } from './upload.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
  providers: [UploadService]
})
export class AppComponent {
  title = 'image-processor-frontend';

  authenticated = false;

  constructor(private readonly keycloak: KeycloakService, private http: HttpClient, private uploadService: UploadService) {
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

  send(){
    this.http.get('http://localhost:80/api/v1/test').subscribe({
      next: (response) =>{
        console.log(response);
      },
      error: (err) => {
        console.log(err);
      }
     });
  }

  onSubmit(fileInput: any, cropHeight: any, cropWidth: any, colorConversion: any, watermark: boolean) {
    const file = fileInput;
    this.uploadService.uploadFileWithParams(file, cropHeight, cropWidth, colorConversion, watermark).subscribe({
      next: (response) =>{
        console.log(response);
      },
      error: (err) => {
        console.log(err);
      }
     });
  }
}