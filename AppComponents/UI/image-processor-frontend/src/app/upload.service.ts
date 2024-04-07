import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
@Injectable()
export class UploadService {

  constructor(private http: HttpClient) {}

  uploadFileWithParams(file: File, cropHeight: number, cropWidth: number, colorConversion: string, watermark: boolean): Observable<any> {

    const formData = new FormData();
    formData.append('file', file);
    formData.append('cropHeight', cropHeight.toString());
    formData.append('cropWidth', cropWidth.toString());
    formData.append('colorConversion', colorConversion);
    formData.append('watermark', watermark ? 'true' : 'false');

    const headers = new HttpHeaders({ 'enctype': 'multipart/form-data' });

    return this.http.post('http://localhost:80/api/v1/images', formData, { headers: headers });

  }
}