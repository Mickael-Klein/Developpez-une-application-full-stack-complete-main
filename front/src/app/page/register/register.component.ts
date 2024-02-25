import { Component, OnInit, Renderer2 } from '@angular/core';
import {
  FormBuilder,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { ButtonComponent } from '../../component/button/button.component';
import { Button } from '../../interface/Button.interface';
import { UserAuthService } from '../../core/service/api/user-auth.service';
import { Router } from '@angular/router';
import { RegisterRequest } from '../../core/service/api/interface/userAuth/request/RegisterRequest';

/**
 * Component for displaying register page.
 * This component allows users to register.
 * @class
 */
@Component({
  selector: 'app-register',
  standalone: true,
  imports: [ReactiveFormsModule, ButtonComponent],
  templateUrl: './register.component.html',
  styleUrl: './register.component.scss',
})
export class RegisterComponent implements OnInit {
  registerForm!: FormGroup;
  emailHasError = false;
  usernameHasError = false;
  passwordHasError = false;
  registerFormHasError = false;
  registerErrorMessage = '';
  registerIsSuccess = false;
  screenWidth!: number;
  responsiveImageShouldBeDisplay = false;

  buttonProps: Button = { colored: true, text: "S'inscrire" };

  constructor(
    private userAuthService: UserAuthService,
    private router: Router,
    private formBuilder: FormBuilder,
    private renderer2: Renderer2
  ) {}

  ngOnInit(): void {
    this.registerForm = this.formBuilder.group({
      email: [null, [Validators.required, Validators.email]],
      username: [null, [Validators.required]],
      password: [
        null,
        [
          Validators.required,
          Validators.pattern(
            /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/
          ),
        ],
      ],
    });

    // Set initial screen width and responsive image display
    this.screenWidth = window.innerWidth;
    if (this.screenWidth < 769) {
      this.responsiveImageShouldBeDisplay = true;
    }

    // Listen for window resize event to adjust responsive image display
    this.renderer2.listen(window, 'resize', (event) => {
      this.screenWidth = window.innerWidth;
      if (this.screenWidth < 769) {
        this.responsiveImageShouldBeDisplay = true;
      } else {
        this.responsiveImageShouldBeDisplay = false;
      }
    });
  }

  /** Handles form submission for user registration */
  onSubmitForm(): void {
    this.emailHasError = false;
    this.usernameHasError = false;
    this.passwordHasError = false;
    this.registerFormHasError = false;
    this.registerErrorMessage = '';

    const emailControl = this.registerForm.controls['email'];
    const isEmailValidInput = emailControl.valid;
    const emailValue = emailControl.value;

    const usernameControl = this.registerForm.controls['username'];
    const isUsernameValidInput = usernameControl.valid;
    const usernameValue = usernameControl.value;

    const passwordControl = this.registerForm.controls['password'];
    const isPasswordValidInput = passwordControl.valid;
    const passwordValue = passwordControl.value;

    if (!isEmailValidInput) {
      this.emailHasError = true;
    }

    if (!isUsernameValidInput) {
      this.usernameHasError = true;
    }

    if (!isPasswordValidInput) {
      this.passwordHasError = true;
    }

    // If any validation fails, stop submission
    if (this.emailHasError || this.usernameHasError || this.passwordHasError) {
      return;
    }

    const registerRequest: RegisterRequest = {
      email: emailValue,
      username: usernameValue,
      password: passwordValue,
    };

    // Send request to register new user
    this.userAuthService.register(registerRequest).subscribe({
      next: (success: boolean) => {
        if (success) {
          // Set success flag, display success to user and redirect after 3 seconds
          setTimeout(() => {
            this.registerIsSuccess = true;
            this.router.navigateByUrl('/login');
          }, 3000);
        }
      },
      error: (error: any) => {
        this.registerFormHasError = true;

        if (error.status === 500) {
          this.registerErrorMessage = 'A error occured, please try again later';
        } else {
          this.registerErrorMessage = error.error.message;
        }
      },
    });
  }

  backToHome(): void {
    this.router.navigateByUrl('/home');
  }
}
