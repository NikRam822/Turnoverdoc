import { Component } from '@angular/core';
import { Router } from "@angular/router";
import { RequestService } from "../../service/http.service";
import { OrderStore } from "../../store/orderStore";
import { FullOrder } from "../../models/orders";


@Component({
  selector: 'app-admin-panel',
  templateUrl: './admin-panel.component.html',
  styleUrls: ['./admin-panel.component.scss']
})
export class AdminPanelComponent {
  constructor(private router: Router, private httpService: RequestService, private orderStore: OrderStore) {
  }

  orders: FullOrder[] | null = []
  username = ""
  statusOfOrder = ""

  ngOnInit(): void {
    this.httpService.postAdminFilterOrders(this.username, this.statusOfOrder).subscribe({
      next: (data) => {
        this.orders = (<FullOrder[]>data.body)
      },
      error: () => alert("Incorrect query")
    });
  }

  orderDetail(order: FullOrder) {
    this.orderStore.update({ fullOrder: order })
    this.router.navigate(['admin-panel/order-detail']);
  }
}
