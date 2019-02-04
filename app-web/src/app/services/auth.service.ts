import { Http } from '@angular/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private serverUrl  = 'api/oauth/token';
  private headers = new Headers(
      {'Content-Type': 'application/x-www-form-urlencoded'}
  );
  constructor(private httpClient: Http) { }

  login(username: string, password: string) {

  }
}
