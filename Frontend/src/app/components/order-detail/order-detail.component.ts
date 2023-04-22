import {Component, OnInit} from '@angular/core';
import {OrderStore} from "../../store/orderStore";
import {Router} from '@angular/router';
import {RequestService} from 'src/app/service/http.service';

@Component({
  selector: 'app-order-detail',
  templateUrl: './order-detail.component.html',
  styleUrls: ['./order-detail.component.css']
})
export class OrderDetailComponent implements OnInit {

  constructor(public orderStore: OrderStore, private httpService: RequestService, private router: Router) {
  }

  ngOnInit(): void {
    console.log(this.orderStore.getValue().order)
  }

  back() {
    this.router.navigate(['/auth']);
  }

  filesMap = new Map();
  declare selectedFiles: FileList;

  selectFile(fileName: String, {event}: { event: any }) {
    this.selectedFiles = event.target.files;
    this.filesMap.set(fileName, this.selectedFiles.item(0))
  }

  upload() {
    this.httpService.postUploadDocs(this.orderStore.getValue().order?.id, this.filesMap).subscribe({
      next: (response) => {
        this.router.navigate(['/auth'])
      },
      error: (error) => {
        if (error.status === 200) {
          this.router.navigate(['/auth'])
        } else {
          console.error(error);
        }
      }
    });

  }

  download(fileName: String) {
    this.httpService.getDownloadDocs(this.orderStore.getValue().order?.id, fileName).subscribe({
      next: (response) => {
        alert("Ok")
      },
      error: (error) => {
        if (error.status === 200) {
          location.href = "http://localhost:8080/api/order/download/" + this.orderStore.getValue().order?.id + "/" + fileName
        } else {
          console.error(error);
        }
      }
    });
  }

  disableButton(fileName: String) {
    if (fileName === "PASSPORT") {
      return this.orderStore.getValue().order?.passportPath === null;
    }

    if (fileName === "CONTRACT") {
      return this.orderStore.getValue().order?.contractPath === null;
    }

    if (fileName === "P_45") {
      return this.orderStore.getValue().order?.p45Path === null;
    }

    if (fileName === "P_60") {
      return this.orderStore.getValue().order?.p60Path === null;
    }

    if (fileName === "P_80") {
      return this.orderStore.getValue().order?.p80Path === null;
    }
    return true;
  }

  disableUploadButton(){
return !(this.filesMap.get('CONTRACT') && this.filesMap.get('PASSPORT'))
  }

  disableConfirmButton(){

    return !(this.orderStore.getValue().order?.contractPath !== null &&
            this.orderStore.getValue().order?.passportPath !== null &&
            this.orderStore.getValue().order?.p45Path !== null &&
            this.orderStore.getValue().order?.p60Path !== null &&
            this.orderStore.getValue().order?.p80Path !== null)
  }

  confirmDocs(){
    this.httpService.postConfirmDocs(this.orderStore.getValue().order?.id).subscribe({
      next: (response) => {
        this.router.navigate(['/auth'])
      },
      error: (error) => {
        if (error.status === 200) {
          this.router.navigate(['/auth'])
        } else {
          console.error(error);
        }
      }
    });
  }
}
