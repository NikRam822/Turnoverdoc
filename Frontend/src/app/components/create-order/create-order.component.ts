import { Component } from '@angular/core';
import { Router } from "@angular/router";
import { RequestService } from "../../service/http.service";
import { NgForm } from "@angular/forms";

@Component({
  selector: 'app-create-order',
  templateUrl: './create-order.component.html',
  styleUrls: ['./create-order.component.scss']
})
export class CreateOrderComponent {
  constructor(private router: Router, private httpService: RequestService) {
  }
  back() {
    this.router.navigate(['/auth']);
  }
  public email: string = "";
  public messenger: string = "";
  public phone: string = "";
  public title = 'angular-proj';

  submit(form: NgForm) {
    this.httpService.postCreateOrder(form.value.phone, form.value.messenger, form.value.email,true,"TELEGRAM",true).subscribe({
      next: () => {
        this.router.navigate(['/auth']);
      },

      error: (err) => {
        if (err.status === 200) {
          this.router.navigate(['/auth']);
        } else {
          console.error(err);
        }
      }
    });

  }
}
