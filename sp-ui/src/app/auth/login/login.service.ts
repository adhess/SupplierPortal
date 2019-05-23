import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {JwtHelperService} from '@auth0/angular-jwt';
import {MatDialog, MatSnackBar} from '@angular/material';
import {Router} from '@angular/router';
import {ProfileComponent} from '../../profile/profile.component';
import {ProfileService} from '../../profile/profile.service';
import * as SockJS from 'sockjs-client';
import * as Stomp from 'stompjs';


@Injectable({
  providedIn: 'root'
})
export class LoginService {


  private serverUrl = 'http://localhost:8080/socket';
  private stompClient;
  notificationSub;

  infoToken: TokenModel;
  expirationDate;
  isExpired;
  isAuthenticated = false;
  httpOptions;
  host = 'API/auth/login';
  public token: string;
  public role: any;
  authorities = [];
  authoritiesLabels = {
    readOrders: 'Voir Les Commandes',
    readAndWriteOrders: 'Voir Et Modifier Les Commandes',
    readInvoices: 'Voir Les Factures',
    readAndWriteInvoices: 'Voir Et Modifier Les Factures',
    readContacts: 'Voir Les Contactes',
    readAndWriteContacts: 'Voir Et Modifier Les Contactes',
  };
  public userInfo: any;

  constructor(private http: HttpClient,
              private snackBar: MatSnackBar,
              private router: Router) {
  }


  public login(user) {
    const httpOptions = {
      headers: new HttpHeaders({'Content-Type': 'application/json'}),
      observe: 'response' as 'response',
    };
    return this.http.post(this.host, user, httpOptions);
  }

  public saveToken(token: string) {
    localStorage.setItem('token_current_user', token);
    this.isAuthenticated = true;
    const helper = new JwtHelperService();
    this.infoToken = helper.decodeToken(token);
    this.expirationDate = helper.getTokenExpirationDate(token);
    this.isExpired = helper.isTokenExpired(token);
    this.token = token;
    this.role = this.getRole(this.infoToken.roles);
    this.getAuthority(this.infoToken.roles);
    console.log(this.role);
    this.httpOptions = {
      headers: new HttpHeaders({'Content-Type': 'application/json', Authorization: token}),
      observe: 'response' as 'response',
    };
    this.getUserInfo();
  }

  public parseJWT() {
    const helper = new JwtHelperService();
    const token = localStorage.getItem('token_current_user');
    this.infoToken = helper.decodeToken(token);
    this.expirationDate = helper.getTokenExpirationDate(token);
    this.isExpired = helper.isTokenExpired(token);
    return token;
  }

  private getRole(roles: any[]) {
    function isAdmin(): boolean {
      for (const r of roles) {
        if (r.valueOf() === 'ADMIN'.valueOf()) {
          return true;
        }
      }
      return false;
    }

    function isSupplier(): boolean {
      for (const r of roles) {
        if (r.valueOf() === 'SUPPLIER'.valueOf()) {
          return true;
        }
      }
      return false;
    }

    function isContact(): boolean {
      for (const r of roles) {
        if (r.valueOf() === 'CONTACT'.valueOf()) {
          return true;
        }
      }
      return false;
    }


    if (isAdmin()) {
      return 'ADMIN';
    } else if (isSupplier()) {
      return 'SUPPLIER';
    } else if (isContact()) {
      return 'CONTACT';
    }
    return 'NONE';
  }


  public isAdmin() {
    if (this.role.valueOf() === 'ADMIN'.valueOf()) {
      return true;
    }
    return false;
  }


  public isSupplier() {
    if (this.role.valueOf() === 'SUPPLIER'.valueOf()) {
      return true;
    }
    return false;
  }


  public isContact() {
    if (this.role.valueOf() === 'CONTACT'.valueOf()) {
      return true;
    }
    return false;
  }

  public openSnackBar(u) {
    this.snackBar.open(u['statusText'] + ' : ' + u['status'], 'Résultat', {
      duration: 2000,
    });
  }

  isReadContact() {
    if (this.infoToken) {
      if (this.isAdmin() || this.isSupplier()) {
        return true;
      }
      if (this.isContact()) {
        for (const r of this.infoToken.roles) {
          if (r.valueOf() === 'readAndWriteContacts'.valueOf() || r.valueOf() === 'readContacts'.valueOf()) {
            return true;
          }
        }
      }
    }
    return false;
  }

  isReadOrder() {
    if (this.infoToken) {
      if (this.isAdmin() || this.isSupplier()) {
        return true;
      }
      if (this.isContact()) {
        for (const r of this.infoToken.roles) {
          if (r.valueOf() === 'readAndWriteOrders'.valueOf() || r.valueOf() === 'readOrders'.valueOf()) {
            return true;
          }
        }
      }
    }
    return false;
  }

  isReadInvoice() {
    if (this.infoToken) {
      if (this.isAdmin() || this.isSupplier()) {
        return true;
      }
      if (this.isContact()) {
        for (const r of this.infoToken.roles) {
          if (r.valueOf() === 'readAndWriteInvoices'.valueOf() || r.valueOf() === 'readInvoices'.valueOf()) {
            return true;
          }
        }
      }
    }
    return false;
  }

  logout() {
    this.isAuthenticated = false;
    this.infoToken = undefined;
    this.expirationDate = undefined;
    this.isExpired = undefined;
    this.token = undefined;
    this.role = 'NONE';
    localStorage.removeItem('token_current_user');
    this.disconnect();
    this.router.navigate(['/login']);
  }

  getUserInfo() {
    this.http.post('/API/auth/auth/getUserInfo', {}, this.httpOptions).subscribe(u => {
      this.userInfo = u['body'];
      console.log(u['body']);
    });
  }


  private getAuthority(roles: any[]) {
    const tab = [];
    for (const r of roles) {
      switch (r.valueOf()) {
        case 'readAndWriteInvoices'.valueOf():
          tab.push(this.authoritiesLabels['readAndWriteInvoices']);
          break;
        case 'readInvoices'.valueOf():
          tab.push(this.authoritiesLabels['readInvoices']);
          break;
        case 'readAndWriteOrders'.valueOf():
          tab.push(this.authoritiesLabels['readAndWriteOrders']);
          break;
        case 'readOrders'.valueOf():
          tab.push(this.authoritiesLabels['readOrders']);
          break;
        case 'readAndWriteContacts'.valueOf():
          tab.push(this.authoritiesLabels['readAndWriteContacts']);
          break;
        case 'readContacts'.valueOf():
          tab.push(this.authoritiesLabels['readContacts']);
          break;
      }
    }
    this.authorities = tab;
  }


  initializeWebSocketConnection() {

    let ws = new SockJS(this.serverUrl);
    this.stompClient = Stomp.over(ws);
    const that = this;
    this.stompClient.connect({}, () => {
      this.notificationSub = that.stompClient.subscribe('/' + this.infoToken.sub, (message) => {
        console.log(message);
        if (message.body) {
          this.snackBar.open(message.body, ' :message', {
            duration: 2000,
          });
        }
      });
    });
  }

  // sendMessage() {
  //   this.stompClient.send('/app/send/message', {}, 'message');
  // }

  disconnect() {
    this.notificationSub.unsubscribe();
  }


}

class TokenModel {
  sub;
  roles = [];
  exp;
}
