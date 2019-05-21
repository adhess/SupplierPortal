import {Component, OnInit} from '@angular/core';
import {ddescribe} from '@angular/core/testing/src/testing_internal';
import {ProfileService} from './profile.service';
import {LoginService} from '../auth/login/login.service';


@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss']
})
export class ProfileComponent implements OnInit {

  constructor(private profileService: ProfileService,
              private loginService: LoginService) {
  }

  ngOnInit() {
  }

  updateUser(form) {
    this.profileService.update(form);
  }
}
