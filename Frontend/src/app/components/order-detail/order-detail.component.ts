import {Component, OnInit} from '@angular/core';
import {OrderStore} from "../../store/orderStore";
import { Router } from '@angular/router';
import { RequestService } from 'src/app/service/http.service';

@Component({
  selector: 'app-order-detail',
  templateUrl: './order-detail.component.html',
  styleUrls: ['./order-detail.component.css']
})
export class OrderDetailComponent {

  constructor(private orderStore: OrderStore, private httpService: RequestService, private router: Router) {
  }

  back(){
      this.router.navigate(['/auth']);
  }
  filesMap = new Map();
  declare selectedFiles: FileList;

  selectFile(fileName:String,{event}: { event: any }) {
    this.selectedFiles = event.target.files;
    this.filesMap.set(fileName,this.selectedFiles.item(0))
    console.log( this.filesMap)
  }

  upload() {
    this.httpService.postUploadDocs(this.orderStore.getValue().order?.id,  this.filesMap).subscribe({
      next: (response) => {
        alert("Ok")
      },
      error: (error) => {
        if (error.status === 200) {
          alert("Ok")
        } else {
          console.error(error);
        }
      }
    });

  }


}
