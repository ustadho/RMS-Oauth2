import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Headers, Http } from '@angular/http';
@Injectable({
  providedIn: 'root'
})
export class ProgramService {
  apiUrl = 'api/programs';
  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type':  'application/json',
      'Authorization': 'my-auth-token'
    })
  };

  constructor(private httpClient: HttpClient) { }

  getAll(): Observable<any> {
    return this.httpClient.get(this.apiUrl);
  }

  findOne(id: number) {
    return this.httpClient.get(this.apiUrl + '/' + id);
  }

  save(p: any, id: number): Promise<void> {
    if (id !== null) {
      return this.httpClient.put(this.apiUrl + '/' + id, p)
      .toPromise()
      .then(() => { console.log('Sukses menyimpan data'); })
      .catch(error => console.log('Error : ' + error));
    } else {
      return this.httpClient.post(this.apiUrl, p)
      .toPromise()
      .then(() => { console.log('Sukses menyimpan data'); })
      .catch(error => console.log('Error : ' + error));
    }
    return null;
  }
}
