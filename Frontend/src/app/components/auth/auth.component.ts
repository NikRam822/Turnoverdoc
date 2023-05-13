import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {RequestService} from "../../service/http.service";
import {Order} from "../../models/orders";
import {NgForm} from "@angular/forms";
import {OrderStore} from "../../store/orderStore";

@Component({
  selector: 'app-auth',
  templateUrl: './auth.component.html',
  styleUrls: ['./auth.component.css']
})
export class AuthComponent implements OnInit {
  constructor(private router: Router, private httpService: RequestService,private orderStore:OrderStore) {
  }

  orders:Order[] | null=[]

  ngOnInit(): void {
    this.httpService.getOrders().subscribe({
      next: (data) => {
        this.orders=(<Order[]>data.body)
      },
      error: () => alert("Incorrect query")
    });
  }

  submit() {
        this.router.navigate(['auth/create-order']);
  }

  orderDetail(order: Order) {
    this.orderStore.update({order:order})
    this.router.navigate(['auth/order-detail']);
  }

}
