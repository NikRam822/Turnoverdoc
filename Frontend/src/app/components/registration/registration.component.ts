import { Component } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { RequestService } from 'src/app/service/http.service';

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.scss']
})
export class RegistrationComponent {
  constructor(private router: Router, private httpService: RequestService) {
  }

  signIn() {
    this.router.navigate(['/']);
  }

  step = 1;

  next() {
    this.step++;
  }
  back() {
    this.step--;
  }
  submit() {
    const contactForm = this.multiStepForm.value.contactsForm;
    const nameForm = this.multiStepForm.value.nameForm;
    const loginForm = this.multiStepForm.value.loginForm
    this.httpService.postReg(contactForm.email, nameForm.firstName, loginForm.password, nameForm.secondName, nameForm.lastName, loginForm.login).subscribe({
      next: () => {
        this.router.navigate(['/']);
      },
      error: (error) => {
        if (error.status === 201) {
          this.router.navigate(['/']);
        } else {
          console.error(error);
        }
      }
    });
  }

  multiStepForm = new FormGroup({
    nameForm: new FormGroup({
      firstName: new FormControl(''),
      lastName: new FormControl(''),
      secondName: new FormControl(''),
    }),
    contactsForm: new FormGroup({
      email: new FormControl(''),
      phone: new FormControl(''),
      messanger: new FormControl(''),
    }),
    loginForm: new FormGroup({
      login: new FormControl(''),
      password: new FormControl(''),
      repPassword: new FormControl(''),
    }),
  });
}
