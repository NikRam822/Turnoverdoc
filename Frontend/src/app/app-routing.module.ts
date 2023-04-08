import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {LoginComponent} from "./login/login.component";
import {AuthComponent} from "./auth/auth.component";



const appRoutes: Routes = [
  {path: 'login', component: LoginComponent/*, canActivate: [true]*/ },
  {path: 'auth', component: AuthComponent/*, canActivate: [AuthGuard]*/},
];


@NgModule({
  imports: [RouterModule.forRoot(appRoutes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
