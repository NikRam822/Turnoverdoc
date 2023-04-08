import {Component} from '@angular/core';
import {Router} from "@angular/router";
import {NgForm} from "@angular/forms";
import {RequestService} from "../service/http.service";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  constructor(private router: Router,private httpService:RequestService) {
  }

  public login: string = "";
  public password: string = "";
  public title = 'angular-proj';

  submit(form: NgForm) {
    alert(form.value.login + " - " + form.value.password)
    this.httpService.postAuth(form.value.login,form.value.password).subscribe({
      next: () => {
        this.router.navigate(['/auth']);
        alert("cool")
      },
      error: () => alert("Incorrect query")
    });

  }
}
