import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {LoginService} from '../auth/login/login.service';

@Injectable({
  providedIn: 'root'
})
export class ContactsService {
  host = '/API/auth/';
  hostP2P = '/API/p2p/';


  constructor(private http: HttpClient, private loginService: LoginService) {
  }

  public getAllUser(): any {
    return this.http.get(this.host + 'admin/find_all_user', this.loginService.httpOptions);
  }

  public getAllSupplierSRM(): any {
    return this.http.get(this.hostP2P + 'admin/getAllSupplierSRM', this.loginService.httpOptions);
  }

  affectSupplier(username, id) {
    return this.http.post(this.hostP2P + 'admin/affectSupplierToSupplierSRM',
      {username, id}, this.loginService.httpOptions);
  }

  getAllContacts(): any {
    return this.http.get(this.host + 'auth/findAllContacts', this.loginService.httpOptions);
  }

  addContact(form: any) {
    return this.http.post(this.host + 'supplier/addContact', form, this.loginService.httpOptions);
  }
}
