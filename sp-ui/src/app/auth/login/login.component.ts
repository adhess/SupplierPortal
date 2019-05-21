import {Component, OnInit} from '@angular/core';
import {LoginService} from './login.service';
import {Router} from '@angular/router';
import {HttpResponse} from '@angular/common/http';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  constructor(private loginService: LoginService, private router: Router) {
  }

  ngOnInit() {
  }

  public onLogin(form) {
    this.loginService.login(form).subscribe((resp: HttpResponse<any>) => {
      const token = resp.headers.get('authorization');
      this.loginService.saveToken(token);
      this.router.navigate(['/home']);
      this.loginService.openSnackBar(resp);
      this.loginService.initializeWebSocketConnection();
    }, err => {
      this.loginService.openSnackBar(err);
    });
  }

  public onLogout() {
    this.loginService.disconnect();
    this.loginService.logout();

  }
}

