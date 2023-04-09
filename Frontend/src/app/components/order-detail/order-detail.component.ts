import {Component, OnInit} from '@angular/core';
import {OrderStore} from "../../store/orderStore";
import { Router } from '@angular/router';
import { RequestService } from 'src/app/service/http.service';

@Component({
  selector: 'app-order-detail',
  templateUrl: './order-detail.component.html',
  styleUrls: ['./order-detail.component.css']
})
export class OrderDetailComponent implements OnInit{

  constructor(private orderStore:OrderStore, private router: Router,private httpService:RequestService) {
  }

  back(){
      this.router.navigate(['/auth']);
  }

  fileToUpload: File | null = null;

  ngOnInit(): void {
    console.log(this.orderStore.getValue().order)
  }

  handleFileInput(files: FileList) {
    this.fileToUpload = files.item(0);
  }



}
