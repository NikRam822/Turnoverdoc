import {Component} from '@angular/core';
import {Router} from "@angular/router";
import {NgForm} from "@angular/forms";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  constructor(private router: Router) {
  }

  public login: string = "";
  public password: string = "";
  public title = 'angular-proj';

  submit(form: NgForm) {
    alert(form.value.login + " - " + form.value.password)

  }
}
