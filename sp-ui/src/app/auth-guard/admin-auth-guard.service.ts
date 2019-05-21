import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot} from '@angular/router';
import {Observable} from 'rxjs';
import {LoginService} from '../auth/login/login.service';

@Injectable({
  providedIn: 'root',
})
export class AdminAuthGuard implements CanActivate {

  constructor(private router: Router,
              private authService: LoginService) {
  }

  canActivate(route: ActivatedRouteSnapshot,
              state: RouterStateSnapshot): Observable<boolean> | Promise<boolean> | boolean {

    for (const role of this.authService.infoToken.roles) {
      if (role.valueOf() === 'ADMIN'.valueOf()) {
        return true;
      }
    }
    return false;
  }
}
