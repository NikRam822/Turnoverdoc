import {Component, OnInit} from '@angular/core';
import {OrderStore} from "../../store/orderStore";

@Component({
  selector: 'app-order-detail',
  templateUrl: './order-detail.component.html',
  styleUrls: ['./order-detail.component.css']
})
export class OrderDetailComponent implements OnInit{

  constructor(private orderStore:OrderStore) {
  }

  ngOnInit(): void {
    console.log(this.orderStore.getValue().order?.id)
  }



}
