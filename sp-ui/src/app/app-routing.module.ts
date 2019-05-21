import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
import {WelcomeComponent} from './welcome/welcome.component';
import {SignupComponent} from './auth/signup/signup.component';
import {LoginComponent} from './auth/login/login.component';
import {HomeComponent} from './home/home.component';
import {EntityComponent} from './entity/entity.component';
import {ContactsComponent} from './contacts/contacts.component';
import {AuthGuardService} from './auth-guard/auth-guard.service';
import {ContactsAuthGuardService} from './auth-guard/contacts-auth-guard.service';
import {OrderAuthGuard} from './auth-guard/Order-auth-guard';
import {InvoiceAuthGuard} from './auth-guard/Invoice-auth-guard';
import {ProfileComponent} from './profile/profile.component';

const routes: Routes = [
  {path: '', component: WelcomeComponent},
  {path: 'signup', component: SignupComponent},
  {path: 'login', component: LoginComponent},
  {path: 'home', component: HomeComponent, canActivate: [AuthGuardService]},
  {path: 'profile', component: ProfileComponent, canActivate: [AuthGuardService]},
  {path: 'i/:type', component: EntityComponent, canActivate: [AuthGuardService, InvoiceAuthGuard]},
  {path: 'c/:type', component: EntityComponent, canActivate: [AuthGuardService, OrderAuthGuard]},
  {path: 'contacts', component: ContactsComponent, canActivate: [AuthGuardService, ContactsAuthGuardService]},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
