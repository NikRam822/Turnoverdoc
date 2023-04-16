import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {AppComponent} from './app.component';
import {LoginComponent} from './components/login/login.component';
import {RouterOutlet, RouterModule, Routes} from "@angular/router";
import {FormsModule} from "@angular/forms";
import {HttpClientModule} from "@angular/common/http";
import {AppRoutingModule} from "./app-routing.module";
import { AuthComponent } from './components/auth/auth.component';
import { RegistrationComponent } from './components/registration/registration.component';
import { CreateOrderComponent } from './components/create-order/create-order.component';
import { OrderDetailComponent } from './components/order-detail/order-detail.component';
import { ReactiveFormsModule } from '@angular/forms';
import { LogadminComponent } from './components/admin-auth/logadmin.component';
import { AdminPanelComponent } from './components/admin-panel/admin-panel.component';
import { AdminPanelDetailComponent } from './components/admin-panel-detail/admin-panel-detail.component'

const appRoutes: Routes = [

  {path: '', component: LoginComponent},
  {path: 'auth', component: AuthComponent},
  {path: 'auth/create-order', component: CreateOrderComponent},
  {path: 'auth/order-detail', component: OrderDetailComponent},

];

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    AuthComponent,
    RegistrationComponent,
    CreateOrderComponent,
    OrderDetailComponent,
    LogadminComponent,
    AdminPanelComponent,
    AdminPanelDetailComponent,
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    AppRoutingModule,
    FormsModule,
    RouterModule.forRoot(appRoutes),
    ReactiveFormsModule
  ],
  bootstrap: [AppComponent]
})

export class AppModule {
}

