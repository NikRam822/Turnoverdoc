import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {LoginComponent} from "./components/login/login.component";
import {AuthComponent} from "./components/auth/auth.component";
import {LoginPageGuard} from "./guards/login-page.guard";
import {AuthGuard} from "./guards/autn-page.guard";
import {RegistrationComponent} from './components/registration/registration.component';
import {CreateOrderComponent} from "./components/create-order/create-order.component";
import {OrderDetailComponent} from "./components/order-detail/order-detail.component";
import {OrderDetailPageGuard} from "./guards/order-detail-page.guard";
import { LogadminComponent } from './components/admin-auth/logadmin.component';
import { AdminPanelComponent } from './components/admin-panel/admin-panel.component';
import { AdminAuthGuard } from './guards/admin-panel-page.guard';
import {AdminPanelDetailComponent} from "./components/admin-panel-detail/admin-panel-detail.component";
import {AdminFullOrderDetailPageGuards} from "./guards/admin-full-order-detail-page.guards";


const appRoutes: Routes = [
  {path: 'login', component: LoginComponent, canActivate: [LoginPageGuard]},
  {path: 'auth', component: AuthComponent, canActivate: [AuthGuard]},
  {path: 'reg', component: RegistrationComponent,},
  {path: 'auth/create-order', component: CreateOrderComponent, canActivate: [AuthGuard]},
  {path: 'auth/order-detail', component: OrderDetailComponent, canActivate: [AuthGuard,OrderDetailPageGuard]},
  {path: 'p4dHwFvPxPJE', component: LogadminComponent},
  {path: 'admin-panel', component: AdminPanelComponent, canActivate: [AdminAuthGuard]},
  {path: 'admin-panel/order-detail', component: AdminPanelDetailComponent, canActivate: [AdminAuthGuard,AdminFullOrderDetailPageGuards]},
];

@NgModule({
  imports: [RouterModule.forRoot(appRoutes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
