import {Component} from '@angular/core';
import {AuthGuardService} from './auth-guard/auth-guard.service';
import {LoginService} from './auth/login/login.service';
import {ProfileService} from './profile/profile.service';


@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'],
})
export class AppComponent {

  constructor(private loginService: LoginService, private profileService: ProfileService) {
  }

}
