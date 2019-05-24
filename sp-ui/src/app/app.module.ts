import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {MaterialModule} from './material.module';
import {SignupComponent} from './auth/signup/signup.component';
import {LoginComponent} from './auth/login/login.component';
import {WelcomeComponent} from './welcome/welcome.component';
import {HomeComponent} from './home/home.component';
import {FlexLayoutModule} from '@angular/flex-layout';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {HttpClientModule} from '@angular/common/http';
import {ProgressSpinnerDialogComponent} from './progress-spinner-dialog/progress-spinner-dialog.component';
import {EntityComponent} from './entity/entity.component';
import {DetailsComponent} from './details/details.component';
import {ContactsComponent} from './contacts/contacts.component';
import {AffectSupplierComponent} from './affect-supplier/affect-supplier.component';
import {AddContactComponent} from './contacts/add-contact/add-contact.component';
import {ProfileComponent} from './profile/profile.component';
import {NgxSpinnerModule} from "ngx-spinner";
import {ChartsModule} from "ng2-charts";
import { MyLineChartComponent } from './my-line-chart/my-line-chart.component';

@NgModule({
  declarations: [
    AppComponent,
    SignupComponent,
    LoginComponent,
    WelcomeComponent,
    HomeComponent,
    ProgressSpinnerDialogComponent,
    EntityComponent,
    DetailsComponent,
    ContactsComponent,
    AffectSupplierComponent,
    AddContactComponent,
    ProfileComponent,
    MyLineChartComponent
  ],
  imports: [
    ChartsModule,
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MaterialModule,
    FlexLayoutModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    NgxSpinnerModule
  ],
  providers: [],
  entryComponents: [ProgressSpinnerDialogComponent, DetailsComponent,
    AffectSupplierComponent, AddContactComponent],
  bootstrap: [AppComponent]
})
export class AppModule {
}
