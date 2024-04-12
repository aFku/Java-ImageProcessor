import { Component } from '@angular/core';
import { KeycloakService } from 'keycloak-angular';
import { HttpClient } from '@angular/common/http';
import { UploadService } from './upload.service';
import * as SockJS from 'sockjs-client';
import * as Stomp from 'stompjs';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
  providers: [UploadService]
})
export class AppComponent {
  title = 'image-processor-frontend';

  authenticated = false;
  stompClient: Stomp.Client = Stomp.client("ws://localhost:80/websocket");

  constructor(private readonly keycloak: KeycloakService, private http: HttpClient, private uploadService: UploadService) {
    this.keycloak = keycloak;
    this.http = http;
    this.uploadService = uploadService;
  }

  async ngOnInit() {
    await this.checkAuthentication();
    await this.connectWebSocket();
  }

  async checkAuthentication() {
    this.authenticated = await this.keycloak.isLoggedIn();
  }

  async connectWebSocket() {
    const token = await this.keycloak.getToken();
    this.stompClient = Stomp.client("ws://localhost:80/websocket"); // + '?access_token=' + token);

    const headers = {
      'Authorization': 'Bearer ' + token,
    };

    var message_callback = function(message: any) {
      console.log(message);
    }

    const connect_callback = (message: any) => {
      this.stompClient.subscribe("/tasks/added_tasks", message_callback, headers);
    };
    
    this.stompClient.connect(headers, connect_callback);

    // console.log("subscribing to /tasks/added_tasks")
    // 
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