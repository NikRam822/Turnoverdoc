import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {RequestService} from "../../service/http.service";

@Component({
  selector: 'app-auth',
  templateUrl: './auth.component.html',
  styleUrls: ['./auth.component.css']
})
export class AuthComponent implements OnInit {
  constructor(private router: Router,private httpService:RequestService) {
  }
  ngOnInit(): void {
this.httpService.getOrders().subscribe({
  next: (data) => {
    console.log(data.body)
  },
  error: () => alert("Incorrect query")
});
  }

}
