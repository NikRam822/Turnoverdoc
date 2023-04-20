import {Injectable} from "@angular/core";
import {CanActivate, Router} from "@angular/router";

@Injectable({
  providedIn: "root"
})

export class LoginPageGuard implements CanActivate {
  constructor(private router: Router) {
  }

  canActivate(): boolean {

    if (document.cookie != "") {
      this.router.navigate(['/auth'])
      return false
    } else {
      return true
    }

  }
}
