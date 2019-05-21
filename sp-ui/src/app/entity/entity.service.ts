import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {LoginService} from '../auth/login/login.service';

@Injectable({
  providedIn: 'root'
})
export class EntityService {
  host = '/API/p2p/auth/order';

  constructor(private http: HttpClient, private loginService: LoginService) {
  }


  getEntitiesNumber(size: number): any {
    return this.http.get(this.host + '/pages/' + size, this.loginService.httpOptions);
  }

  getEntities(page, size): any {
    return this.http.get(this.host + '/' + page + '/' + size, this.loginService.httpOptions);
  }
}
