import {Component, OnInit} from '@angular/core';
import {NgForm} from '@angular/forms';
import {HttpResponse} from '@angular/common/http';
import {SignupService} from './signup.service';
import {LoginService} from '../login/login.service';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.scss']
})
export class SignupComponent implements OnInit {

  constructor(private signupService: SignupService, private loginService: LoginService) {
  }

  ngOnInit() {
  }

  onSignUp(form: NgForm) {
    this.signupService.signUp(form).subscribe((resp: HttpResponse<any>) => {
      this.loginService.openSnackBar(resp);
    }, err => {
      this.loginService.openSnackBar(err);
    });
  }
}
