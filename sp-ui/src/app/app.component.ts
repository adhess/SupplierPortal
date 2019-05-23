import {Component} from '@angular/core';
import {AuthGuardService} from './auth-guard/auth-guard.service';
import {LoginService} from './auth/login/login.service';
import {ProfileService} from './profile/profile.service';
import {ActivatedRoute} from "@angular/router";


@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'],
})
export class AppComponent {
  selected = {};
  value = 2;
  constructor(public loginService: LoginService, private profileService: ProfileService) {
    this.selected['home'] = true;
  }

  exception() {
    alert('Not Yet Implemented...');
  }

  select(filed: string) {
    const obj = {};
    obj[filed] = true;
    this.selected = obj;
  }
}
