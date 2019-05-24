import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {LoginService} from "../auth/login/login.service";

@Injectable({
  providedIn: 'root'
})
export class HomeService {
  host = '/API/p2p/auth/';

  constructor(private http: HttpClient, private loginService: LoginService) { }

  getInfoStatusCharts() {
    return this.http.get(this.host + '/getInfoStatusCharts/', this.loginService.httpOptions);
  }
}
