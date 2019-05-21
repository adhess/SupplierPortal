import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot} from '@angular/router';
import {Observable} from 'rxjs';
import {LoginService} from '../auth/login/login.service';

@Injectable({
  providedIn: 'root',
})
export class OrderAuthGuard implements CanActivate {

  constructor(private router: Router,
              private loginService: LoginService) {
  }


  canActivate(route: ActivatedRouteSnapshot,
              state: RouterStateSnapshot): Observable<boolean> | Promise<boolean> | boolean {
    if (this.loginService.isReadOrder()) {
      return true;
    }

    this.router.navigate(['/home'], {queryParams: {returnUrl: state.url}});
    return false;
  }
}
