import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class SignupService {
  host = 'API/auth/signup';

  constructor(private http: HttpClient) { }


  public signUp(user) {
    const httpOptions = {
      headers: new HttpHeaders({'Content-Type': 'application/json'}),
      observe: 'response' as 'response',
    };
    return this.http.post(this.host, user, httpOptions);
  }
}
