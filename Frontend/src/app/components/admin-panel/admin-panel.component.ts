import { Component } from '@angular/core';
import {Router} from "@angular/router";
import {RequestService} from "../../service/http.service";
import {OrderStore} from "../../store/orderStore";
import {FullOrder} from "../../models/orders";


@Component({
  selector: 'app-admin-panel',
  templateUrl: './admin-panel.component.html',
  styleUrls: ['./admin-panel.component.css']
})
export class AdminPanelComponent {
  constructor(private router: Router, private httpService: RequestService,private orderStore:OrderStore) {
  }

  orders:FullOrder[] | null=[]
  username =""
  statusOfOrder = ""

  ngOnInit(): void {
    this.httpService.postAdminFilterOrders(this.username,this.statusOfOrder).subscribe({
      next: (data) => {
        this.orders=(<FullOrder[]>data.body)
      },
      error: () => alert("Incorrect query")
    });
  }

  submit() {
    /*this.router.navigate(['auth/create-order']);*/
  }
//todo: дсделать страницу для смены статуса заявки
  orderDetail(order: FullOrder) {
    this.orderStore.update({fullOrder:order})
    alert(this.orderStore.getValue().fullOrder.id)
  }
}
