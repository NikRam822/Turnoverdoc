import { Component } from '@angular/core';
import { Router } from "@angular/router";
import { RequestService } from "../../service/http.service";
import { OrderStore } from "../../store/orderStore";

@Component({
  selector: 'app-admin-panel-detail',
  templateUrl: './admin-panel-detail.component.html',
  styleUrls: ['./admin-panel-detail.component.scss']
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

  filesMap = new Map();
  declare selectedFiles: FileList;

  selectFile(fileName: String, { event }: { event: any }) {
    this.selectedFiles = event.target.files;
    this.filesMap.set(fileName, this.selectedFiles.item(0))
  }

  upload() {
    this.httpService.postUploadDocs(this.orderStore.getValue().fullOrder?.id, this.filesMap).subscribe({
      next: (response) => {
        this.router.navigate(['/admin-panel'])
      },
      error: (error) => {
        if (error.status === 200) {
          this.router.navigate(['/admin-panel'])
        } else {
          console.error(error);
        }
      }
    });

  }

  download(fileName: String) {
    this.httpService.getDownloadDocs(this.orderStore.getValue().fullOrder?.id, fileName).subscribe({
      next: (response) => {
        alert("Ok")
      },
      error: (error) => {
        if (error.status === 200) {
          location.href = "http://localhost:8080/api/order/download/" + this.orderStore.getValue().fullOrder?.id + "/" + fileName
        } else {
          console.error(error);
        }
      }
    });
  }

  disableButton(fileName: String) {
    if (fileName === "PASSPORT") {
      return this.orderStore.getValue().fullOrder?.passportPath === null;
    }

    if (fileName === "CONTRACT") {
      return this.orderStore.getValue().fullOrder?.contractPath === null;
    }

    if (fileName === "P_45") {
      return this.orderStore.getValue().fullOrder?.p45Path === null;
    }

    if (fileName === "P_60") {
      return this.orderStore.getValue().fullOrder?.p60Path === null;
    }

    if (fileName === "P_80") {
      return this.orderStore.getValue().fullOrder?.p80Path === null;
    }
    return true;
  }

  disableUploadButton() {
    return !(this.filesMap.get('CONTRACT') && this.filesMap.get('PASSPORT'))
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
