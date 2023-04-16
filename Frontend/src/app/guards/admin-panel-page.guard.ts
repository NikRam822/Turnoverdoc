
import {Injectable} from "@angular/core";
import { CanActivate, Router} from "@angular/router";
import jwt_decode from "jwt-decode";
@Injectable({
  providedIn: "root"
})

export class AdminAuthGuard implements CanActivate {
  constructor(private router: Router) {
  }

  canActivate(): boolean {


    if (document.cookie != "") {
      const token = document.cookie.split('=')[1]
      const decoded = jwt_decode(token)
      if (decoded['roles'][0] === "ROLE_SUPER_ADMIN" || decoded['roles'][0] === "ROLE_ADMIN"){
        return true
      }else{
        this.router.navigate(["/"]);
        return false
      }

    } else {
      this.router.navigate(["/"]);
      return false
    }
  }
}
