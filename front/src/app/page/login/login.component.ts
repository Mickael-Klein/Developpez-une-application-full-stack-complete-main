import { Component, OnDestroy, OnInit, Renderer2 } from '@angular/core';
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
import { Subscription } from 'rxjs';

/**
 * Component for displaying login page.
 * This component allows users to input their credentials and authenticate themselves.
 * Upon successful login, users are redirected to the articles page.
 * If there is an error during login, appropriate error messages are displayed.
 * @class
 */
@Component({
  selector: 'app-login',
  standalone: true,
  imports: [ReactiveFormsModule, ButtonComponent],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss',
})
export class LoginComponent implements OnInit, OnDestroy {
  loginForm!: FormGroup;
  emailOrUsernameHasError = false;
  passwordHasError = false;
  credentialsError = false;
  credentialsErrorMessage = '';
  screenWidth!: number;
  responsiveImageShouldBeDisplay = false;
  buttonProps: Button = { colored: true, text: 'Se connecter' };

  loginSubscription!: Subscription;
  sessionSubscription!: Subscription;

  constructor(
    private userAuthService: UserAuthService,
    private sessionService: SessionService,
    private router: Router,
    private formBuilder: FormBuilder,
    private renderer2: Renderer2
  ) {}

  ngOnInit(): void {
    this.loginForm = this.formBuilder.group({
      emailOrUsername: [null, [Validators.required]],
      password: [null, [Validators.required]],
    });

    // Detect screen width and set responsive image display
    this.screenWidth = window.innerWidth;
    if (this.screenWidth < 769) {
      this.responsiveImageShouldBeDisplay = true;
    }

    // Listen for window resize events to update screen width and responsive image display
    this.renderer2.listen(window, 'resize', (event) => {
      this.screenWidth = window.innerWidth;
      if (this.screenWidth < 769) {
        this.responsiveImageShouldBeDisplay = true;
      } else {
        this.responsiveImageShouldBeDisplay = false;
      }
    });
  }

  ngOnDestroy(): void {
    if (this.loginSubscription) {
      this.loginSubscription.unsubscribe();
    }
    if (this.sessionSubscription) {
      this.sessionSubscription.unsubscribe();
    }
  }

  /**
   * Handles form submission upon user login attempt.
   * Validates user inputs, performs login request, and handles response accordingly.
   */
  onSubmitForm(): void {
    // Reset error flags and messages
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

    // If there are input errors, abort
    if (this.emailOrUsernameHasError || this.passwordHasError) {
      return;
    }

    const loginRequest: LoginRequest = {
      emailOrUsername: emailOrUsernameValue,
      password: passwordValue,
    };

    // Perform login request and handle response
    this.loginSubscription = this.userAuthService
      .login(loginRequest)
      .subscribe({
        next: (response: string) => {
          console.log('response : ', response);
          // Log in user and navigate to articles page
          this.sessionSubscription = this.sessionService
            .logIn(response)
            .subscribe((response: boolean) => {
              if (response) {
                this.router.navigateByUrl('/articles');
              } else {
                // Display credentials error message if login fails
                this.credentialsError = true;
                this.credentialsErrorMessage =
                  'An error occured, try again later';
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
