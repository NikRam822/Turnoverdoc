import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {LoginComponent} from "./components/login/login.component";
import {AuthComponent} from "./components/auth/auth.component";
import {LoginPageGuard} from "./guards/login-page.guard";
import {AuthGuard} from "./guards/autn-page.guard";
import {RegistrationComponent} from './components/registration/registration.component';
import {CreateOrderComponent} from "./components/create-order/create-order.component";


const appRoutes: Routes = [
  {path: 'login', component: LoginComponent, canActivate: [LoginPageGuard]},
  {path: 'auth', component: AuthComponent, canActivate: [AuthGuard]},
  {path: 'reg', component: RegistrationComponent,},
  {path: 'auth/create-order', component: CreateOrderComponent, canActivate: [AuthGuard]},
];

@NgModule({
  imports: [RouterModule.forRoot(appRoutes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
