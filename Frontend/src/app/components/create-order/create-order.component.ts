import { Component } from '@angular/core';
import {Router} from "@angular/router";
import {RequestService} from "../../service/http.service";
import {NgForm} from "@angular/forms";

@Component({
  selector: 'app-create-order',
  templateUrl: './create-order.component.html',
  styleUrls: ['./create-order.component.css']
})
export class CreateOrderComponent {
  constructor(private router: Router,private httpService:RequestService) {
  }
  back(){
    this.router.navigate(['/auth']);
  }
  public messenger: string = "";
  public phone: string = "";
  public title = 'angular-proj';

  submit(form: NgForm) {
    this.httpService.postCreateOrder(form.value.phone,form.value.messenger).subscribe({
      next: () => {
        this.router.navigate(['/auth']);
      },
      error: () => alert("Incorrect query")
    });

  }
}
