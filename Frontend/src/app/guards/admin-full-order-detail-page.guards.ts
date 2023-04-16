import {Injectable} from "@angular/core";
import {CanActivate, Router} from "@angular/router";
import {OrderStore} from "../store/orderStore";

@Injectable({
  providedIn: "root"
})

export class AdminFullOrderDetailPageGuards implements CanActivate {
  constructor(private router: Router,private orderStore: OrderStore) {
  }

  canActivate(): boolean {

    if (this.orderStore.getValue().fullOrder===undefined) {
      this.router.navigate(['/admin-panel'])
      return false
    } else {
      return true
    }

  }
}
