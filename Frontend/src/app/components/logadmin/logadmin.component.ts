import { Component } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { RequestService } from 'src/app/service/http.service';

@Component({
  selector: 'app-logadmin',
  templateUrl: './logadmin.component.html',
  styleUrls: ['./logadmin.component.css']
})
export class LogadminComponent {
  constructor(private router: Router,private httpService:RequestService) {
  }

  signIn(){
    const adminForm = this.adminAuthForm.value;
    this.httpService.postAdminAuth(adminForm.username, adminForm.password).subscribe({
      next: () => {
        this.router.navigate(['/admin-panel']);
      },
      error: (error) => {
        if (error.status === 400) {
          alert('Incorect data')
        } else {
          console.error(error);
        }
      }
    });
  }

  adminAuthForm = new FormGroup({
    username: new FormControl('', Validators.required),
    password: new FormControl('', Validators.required),
  })
}
