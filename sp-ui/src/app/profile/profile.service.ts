import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {LoginService} from '../auth/login/login.service';

@Injectable({
  providedIn: 'root'
})
export class ProfileService {

  constructor(private http: HttpClient, private loginService: LoginService) {
  }

  update(form: any) {
    this.http.put('API/auth/auth/updateCurrentUser', form, this.loginService.httpOptions).subscribe(u => {
        this.loginService.openSnackBar(u);
      }, error1 => this.loginService.openSnackBar(error1)
    );
  }
}
