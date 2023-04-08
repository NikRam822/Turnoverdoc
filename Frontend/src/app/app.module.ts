import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {AppComponent} from './app.component';
import {LoginComponent} from './login/login.component';
import {RouterOutlet, RouterModule, Routes} from "@angular/router";
import {FormsModule} from "@angular/forms";
import {HttpClientModule} from "@angular/common/http";
import {AppRoutingModule} from "./app-routing.module";
import { AuthComponent } from './auth/auth.component';
import { RegistrationComponent } from './registration/registration.component';

const appRoutes: Routes = [

  {path: '', component: LoginComponent},
  {path: 'auth', component: AuthComponent},
];

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    AuthComponent,
    RegistrationComponent,
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    AppRoutingModule,
    FormsModule,
    RouterModule.forRoot(appRoutes)
  ],
  bootstrap: [AppComponent]
})

export class AppModule {
}

