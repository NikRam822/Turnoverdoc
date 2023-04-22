import {Component} from '@angular/core';
import {Router} from "@angular/router";
import {RequestService} from "../../service/http.service";
import {OrderStore} from "../../store/orderStore";

@Component({
  selector: 'app-admin-panel-detail',
  templateUrl: './admin-panel-detail.component.html',
  styleUrls: ['./admin-panel-detail.component.css']
})
export class AdminPanelDetailComponent {
  constructor(private router: Router, private httpService: RequestService, public orderStore: OrderStore) {
  }

  status: String = ""
  orderId: String = ""

  ngOnInit(): void {
    this.status = this.orderStore.getValue().fullOrder.status
    this.orderId = String(this.orderStore.getValue().fullOrder.id)
  }

  back() {
    this.router.navigate(['/admin-panel']);
  }

  submit() {
    this.httpService.postAdminChangeStatus(String(this.orderStore.getValue().fullOrder.id), String(this.status)).subscribe({
      next: (data) => {
        this.router.navigate(['/admin-panel']);
      },
      error: (err) => {
        if (err.status === 200) {
          this.router.navigate(['/admin-panel']);
        } else {
          console.error(err);
          alert("Incorrect query")
        }
      }
    });
  }
}
