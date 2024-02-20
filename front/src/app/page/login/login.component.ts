import { Component, OnInit } from '@angular/core';
import { UserAuthService } from '../../core/service/api/user-auth.service';
import { SessionService } from '../../core/service/session/session.service';
import { Router } from '@angular/router';
import {
  FormBuilder,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { ButtonComponent } from '../../component/button/button.component';
import { Button } from '../../interface/Button.interface';
import { LoginRequest } from '../../core/service/api/interface/userAuth/request/LoginRequest';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [ReactiveFormsModule, ButtonComponent],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss',
})
export class LoginComponent implements OnInit {
  loginForm!: FormGroup;
  emailOrUsernameHasError = false;
  passwordHasError = false;
  credentialsError = false;
  credentialsErrorMessage = '';

  buttonProps: Button = { colored: true, text: 'Se connecter' };

  constructor(
    private userAuthService: UserAuthService,
    private sessionService: SessionService,
    private router: Router,
    private formBuilder: FormBuilder
  ) {}

  ngOnInit(): void {
    this.loginForm = this.formBuilder.group({
      emailOrUsername: [null, [Validators.required]],
      password: [null, [Validators.required]],
    });
  }

  onSubmitForm(): void {
    this.emailOrUsernameHasError = false;
    this.passwordHasError = false;
    this.credentialsError = false;
    this.credentialsErrorMessage = '';

    const emailOrUsernameControl = this.loginForm.controls['emailOrUsername'];
    const isEmailOrUsernameValidInput = emailOrUsernameControl.valid;
    const emailOrUsernameValue = emailOrUsernameControl.value;

    const passwordControl = this.loginForm.controls['password'];
    const isPasswordValidInput = passwordControl.valid;
    const passwordValue = passwordControl.value;

    if (!isEmailOrUsernameValidInput) {
      this.emailOrUsernameHasError = true;
    }

    if (!isPasswordValidInput) {
      this.passwordHasError = true;
    }

    if (this.emailOrUsernameHasError || this.passwordHasError) {
      return;
    }

    const loginRequest: LoginRequest = {
      emailOrUsername: emailOrUsernameValue,
      password: passwordValue,
    };

    this.userAuthService.login(loginRequest).subscribe({
      next: (response: string) => {
        console.log('response : ', response);
        this.sessionService.logIn(response).subscribe((response: boolean) => {
          if (response) {
            this.router.navigateByUrl('/articles');
          } else {
            this.credentialsError = true;
            this.credentialsErrorMessage = 'An error occured, try again later';
          }
        });
      },
      error: (err: any) => {
        this.credentialsError = true;

        if (err.status === 500) {
          this.credentialsErrorMessage = 'An error occured, try again later';
        } else {
          this.credentialsErrorMessage = 'Invalid Credentials';
        }
      },
    });
  }

  backToHome() {
    this.router.navigateByUrl('/home');
  }
}
