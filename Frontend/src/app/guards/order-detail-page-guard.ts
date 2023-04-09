import {Injectable} from "@angular/core";
import {CanActivate, Router} from "@angular/router";
import {OrderStore} from "../store/orderStore";

@Injectable({
  providedIn: "root"
})

export class OrderDetailPageGuard implements CanActivate {
  constructor(private router: Router,private orderStore: OrderStore) {
  }

  canActivate(): boolean {

    if (this.orderStore.getValue().order===undefined) {
      this.router.navigate(['/auth'])
      return false
    } else {
      return true
    }

  }
}
