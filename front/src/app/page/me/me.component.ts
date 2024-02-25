import { Component, OnInit } from '@angular/core';
import { SessionService } from '../../core/service/session/session.service';
import { Router } from '@angular/router';
import { UserService } from '../../core/service/api/user.service';
import { User } from '../../core/model/User.model';
import { Observable, catchError, map, of } from 'rxjs';
import { Subject } from '../../core/model/Subject.model';
import {
  FormBuilder,
  FormControl,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { CommonModule } from '@angular/common';
import { SubjectService } from '../../core/service/api/subject.service';
import { ButtonComponent } from '../../component/button/button.component';
import { Button } from '../../interface/Button.interface';
import { ThemeCard } from '../../interface/ThemeCard.interface';
import { ThemeCardComponent } from '../../component/theme-card/theme-card.component';
import { UpdateUserRequest } from '../../core/service/api/interface/user/request/UpdateUserRequest';

/**
 * Component for displaying me page.
 * This component allows users to update their infos, see to which subjects they are subscribed, and unsubscribe from them.
 * @class
 */
@Component({
  selector: 'app-me',
  standalone: true,
  imports: [
    ReactiveFormsModule,
    CommonModule,
    ButtonComponent,
    ThemeCardComponent,
  ],
  templateUrl: './me.component.html',
  styleUrl: './me.component.scss',
})
export class MeComponent implements OnInit {
  user$!: Observable<User | undefined>;
  userFetchError = false;
  subjects$!: Observable<Subject[]>;
  subjectsFetchError = false;
  updateUserForm!: FormGroup;
  emailError = false;
  emailAlreadyTaken = false;
  usernameError = false;
  usernameAlreadyTaken = false;
  passwordError = false;
  updateUserError = false;
  isSubmitting = false;
  updateUserSuccess = false;

  submitFormButtonProps: Button = {
    text: 'Sauvegarder',
    colored: true,
  };

  unsubscribeButtonProps: Button = {
    text: 'Se désabonner',
    colored: true,
  };

  constructor(
    private sessionService: SessionService,
    private subjectService: SubjectService,
    private router: Router,
    private userService: UserService,
    private formBuilder: FormBuilder
  ) {}

  ngOnInit(): void {
    this.updateUserForm = this.formBuilder.group({
      email: [null, [Validators.required, Validators.email]],
      username: [null, Validators.required],
      password: [
        null,
        [
          Validators.pattern(
            /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/
          ),
        ],
      ],
    });

    this.user$ = this.sessionService.$getUser().pipe(
      map((user) => {
        this.userFetchError = false;
        return user;
      }),
      catchError((error: any) => {
        console.log(error);
        this.userFetchError = true;
        return of(undefined);
      })
    );

    // Populate the update user form with initial user data
    this.user$.subscribe((user) => {
      if (user) {
        this.updateUserForm.patchValue({
          email: user.email,
          username: user.username,
        });
      }
    });

    this.subjects$ = this.subjectService.$getSubjects();
  }

  /** Updates user information */
  updateUser() {
    if (!this.isSubmitting) {
      this.isSubmitting = true;
      this.emailAlreadyTaken = false;
      this.emailError = false;
      this.usernameError = false;
      this.usernameAlreadyTaken = false;
      this.passwordError = false;
      this.updateUserError = false;
      this.updateUserSuccess = false;

      const emailFormControl = this.updateUserForm.controls['email'];
      const isEmailValid = emailFormControl.valid;
      const emailValue = emailFormControl.value;

      const usernameFormControl = this.updateUserForm.controls['username'];
      const isUsernameValid = usernameFormControl.valid;
      const usernameValue = usernameFormControl.value;

      const passwordFormControl = this.updateUserForm.controls['password'];
      const isPasswordValid = passwordFormControl.valid;
      const passwordValue = passwordFormControl.value;

      if (!isEmailValid) {
        this.emailError = true;
      }
      if (!isUsernameValid) {
        this.usernameError = true;
      }

      if (passwordValue !== null) {
        if (passwordValue.length > 0 && !isPasswordValid) {
          this.passwordError = true;
        }
      }

      // If any validation error is present, stop submission
      if (this.emailError || this.usernameError || this.passwordError) {
        this.isSubmitting = false;
        return;
      }

      const updateUserRequest: UpdateUserRequest = {
        email: emailValue,
        username: usernameValue,
        ...(passwordValue !== null &&
          passwordValue.length > 0 && { password: passwordValue }),
      };

      // If user data is not modified, stop submission
      if (
        this.sessionService.user?.email === updateUserRequest.email &&
        this.sessionService.user.username === updateUserRequest.username &&
        !updateUserRequest.password
      ) {
        this.isSubmitting = false;
        return;
      }

      // Send update request to the server
      this.userService
        .updateMe(updateUserRequest)
        .pipe(
          map((user: User) => {
            // Update session user data
            this.sessionService.updateUser(user);
            // Patch form values with updated user data
            this.updateUserForm.patchValue({
              email: user.email,
              username: user.username,
            });
            // Set success flag and reset form submission state
            this.updateUserSuccess = true;
            this.isSubmitting = false;
            // Reset success flag after 3 seconds
            setTimeout(() => {
              this.updateUserSuccess = false;
            }, 3000);
          }),
          catchError((error: any) => {
            switch (error.message) {
              case 'Email is already registered':
                this.emailAlreadyTaken = true;
                break;
              case 'Username is already registered':
                this.usernameAlreadyTaken = true;
                break;
              default:
                console.log('error', error);
                this.updateUserError = true;
            }
            this.isSubmitting = false;
            return of(null);
          })
        )
        .subscribe();
    }
  }

  /** Logs out the user and navigates to the home page */
  logout() {
    this.sessionService.logOut();
    this.router.navigateByUrl('/home');
  }

  /**
   * Constructs theme card properties for the given subject.
   * @param subject - The subject for which theme card properties are being constructed.
   * @returns themeCardProps - The constructed theme card properties.
   */
  toThemeCardProps(subject: Subject): ThemeCard {
    const themeCardProps: ThemeCard = {
      subjectId: subject.id,
      subjectName: subject.name,
      subjectContent: subject.description,
      isForUnsubscribe: true,
    };
    return themeCardProps;
  }
}
