import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {RequestService} from "../../service/http.service";
import {Order} from "../../models/orders";
import {NgForm} from "@angular/forms";

@Component({
  selector: 'app-auth',
  templateUrl: './auth.component.html',
  styleUrls: ['./auth.component.css']
})
export class AuthComponent implements OnInit {
  constructor(private router: Router, private httpService: RequestService) {
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

}
