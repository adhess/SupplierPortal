import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, NgForm} from '@angular/forms';
import {HttpResponse} from '@angular/common/http';
import {SignupService} from './signup.service';
import {LoginService} from '../login/login.service';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.scss']
})
export class SignupComponent implements OnInit {
  firstFormGroup: FormGroup;
  secondFormGroup: FormGroup;
  thirdFormGroup: FormGroup;

  constructor(private signupService: SignupService, private loginService: LoginService,
              private formBuilder: FormBuilder) {
  }

  ngOnInit() {
    this.firstFormGroup = this.formBuilder.group({
      username: [''],
      password: [''],
      name: [''],
      telephone: [''],
      fax: [''],
      email: [''],
    });
    this.secondFormGroup = this.formBuilder.group({
      city: [''],
      state: [''],
      zipCode: [''],
    });
    this.thirdFormGroup = this.formBuilder.group({
      activitySector: [''],
      employeeNumber: [''],
      company_number: [''],
      tva: [''],
    });
  }


  form;
  onSignUp() {
    this.form = this.firstFormGroup.value;
    this.form['city'] = this.secondFormGroup.value['city'];
    this.form['state'] = this.secondFormGroup.value['state'];
    this.form['zipCode'] = this.secondFormGroup.value['zipCode'];
    this.form['activitySector'] = this.thirdFormGroup.value['activitySector'];
    this.form['company_number'] = this.thirdFormGroup.value['company_number'];
    this.form['employeeNumber'] = this.thirdFormGroup.value['employeeNumber'];

    this.signupService.signUp(this.form).subscribe((resp: HttpResponse<any>) => {
      this.loginService.openSnackBar(resp);
    }, err => {
      this.loginService.openSnackBar(err);
    });
  }
}
