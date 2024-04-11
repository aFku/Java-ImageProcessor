import { Component } from '@angular/core';
import { KeycloakService } from 'keycloak-angular';
import { HttpClient } from '@angular/common/http';
import { UploadService } from './upload.service';
import * as SockJS from 'sockjs-client';
import * as Stomp from 'stompjs';

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

    console.log(this.keycloak.getToken());

    var stompClient = Stomp.client("ws://localhost:80/websocket" + '?access_token=' + this.keycloak.getToken());

    var error_callback = function(error: any) {
      // display the error's message header:
      alert(error.headers.message);
    };

    var headers = {
      // 'authorization': 'Bearer '+ await this.keycloak.getToken(),
    }
    
    stompClient.connect(headers, error_callback);

    // var message_callback = function(message: any) {
    //   console.log(message.body);
    // }

    // console.log(this.keycloak.loadUserProfile().then((user: any) => {
    //   console.log(user.id);
    // }));

    // var subscription = stompClient.subscribe("/queue/test", message_callback, headers);
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