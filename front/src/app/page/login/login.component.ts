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
import { FormInputComponent } from '../../component/form-input/form-input.component';
import { formInput } from '../../interface/formInput.interface';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [FormInputComponent, ReactiveFormsModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss',
})
export class LoginComponent implements OnInit {
  loginForm!: FormGroup;

  emailOrUsernameFormInputData: formInput = {
    title: 'emailOrUsername',
    inputTitle: "E-mail ou nom d'utilisateur",
    errorMessage: 'An email or username is required',
  };
  passwordFormInputData: formInput = {
    title: 'password',
    inputTitle: 'Mot de passe',
    errorMessage: 'A password is required',
  };

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

  onSubmitLoginForm() {
    console.log(this.loginForm.value);
  }
}
