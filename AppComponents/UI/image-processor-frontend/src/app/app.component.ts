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

  url = "http://localhost:80/api/v1/images";

  authenticated = false;
  stompClient: Stomp.Client = Stomp.client("ws://localhost:80/websocket");

  notificationList = "";
  imageList = "";

  constructor(private readonly keycloak: KeycloakService, private http: HttpClient, private uploadService: UploadService) {
    this.keycloak = keycloak;
    this.http = http;
    this.uploadService = uploadService;
  }

  async ngOnInit() {
    await this.checkAuthentication();
    if (this.authenticated) {
      await this.connectWebSocket(this);
    }
    this.getOwnedImages();
  }

  async checkAuthentication() {
    this.authenticated = await this.keycloak.isLoggedIn();
  }

  async connectWebSocket(app: AppComponent) {
    const token = await this.keycloak.getToken();
    this.stompClient = Stomp.client("ws://localhost:80/websocket");

    const headers = {
      'Authorization': 'Bearer ' + token,
    };

    var message_callback = function(message: any) {
      // Add to notification list
      console.log(message.body)
      app.notificationList += "<li>" + JSON.stringify(message.body) + "</li>";
    }

    const connect_callback = (message: any) => {
      this.keycloak.loadUserProfile().then(profile => {
        this.stompClient.subscribe("/user/" + profile.id + "/queue/notification", message_callback, headers);
      });
    };
    
    this.stompClient.connect(headers, connect_callback);
  }

  login() {
    this.keycloak.login();
  }

  logout() {
    this.keycloak.logout();
  }

  onSubmit(fileInput: any, cropHeight: any, cropWidth: any, colorConversion: any, watermark: boolean) {
    const file = fileInput;
    this.uploadService.uploadFileWithParams(file, cropHeight, cropWidth, colorConversion, watermark).subscribe({
      next: (response) =>{
        alert(JSON.stringify(response))
      },
      error: (err) => {
        alert(JSON.stringify(err.error));
      }
     });
  }

  getOwnedImages(){
    this.http.get(this.url).subscribe({
      next: async (response) =>{
        let resSTR = JSON.stringify(response);
        let resJSON = JSON.parse(resSTR);
        this.generateHtmlListOfImages(resJSON.data).then(images => {
          this.imageList = images;
        })
      },
      error: (err) => {
        console.log(err);
      }
     });
  }

  async generateHtmlListOfImages(objects: Array<any>){
    var html = "";
    objects.forEach((object) => {
      html += `<div>
                <div style="width: 1000px; height: 600px;">
                  <image src="http://localhost:80${object.processedContentUri}" width="50%" height="50%"/>
                </div>
                <div style="width: 1000px; height: 600px;">
                  <image src="http://localhost:80${object.rawContentUri}" width="50%" height="50%"/>
                </div>
                <ul>
                  <li>Raw Filename: ${object.rawFilename}</li>
                  <li>Processed Filename: ${object.processedFilename}</li>
                  <li>Color Conversion: ${object.colorConversion}</li>
                  <li>Crop height: ${object.cropHeight}</li>
                  <li>Crop width: ${object.cropWidth}</li>
                  <li>Watermark: ${object.watermark}</li>
                </ul>
              </div>`;
    });
    return html;
  }
}