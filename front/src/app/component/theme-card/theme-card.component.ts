import { Component, Input, OnInit } from '@angular/core';
import { ThemeCard } from '../../interface/ThemeCard.interface';
import { SessionService } from '../../core/service/session/session.service';
import { Observable, catchError, map, of, switchMap } from 'rxjs';
import { User } from '../../core/model/User.model';
import { ButtonComponent } from '../button/button.component';
import { Button } from '../../interface/Button.interface';
import { CommonModule } from '@angular/common';
import { UserService } from '../../core/service/api/user.service';

@Component({
  selector: 'app-theme-card',
  standalone: true,
  imports: [ButtonComponent, CommonModule],
  templateUrl: './theme-card.component.html',
  styleUrl: './theme-card.component.scss',
})
export class ThemeCardComponent implements OnInit {
  @Input() themeCardProps!: ThemeCard;

  user$!: Observable<User | undefined>;
  subjectId!: number;
  isError = false;

  buttonPropsNotSub: Button = { text: "S'abonner", colored: true };
  buttonPropsIsSub: Button = {
    text: "S'abonner",
    colored: false,
    unclickable: true,
  };
  buttonPropsUnsubscribe: Button = { text: 'Se désabonner', colored: true };

  constructor(
    private sessionService: SessionService,
    private userService: UserService
  ) {}

  ngOnInit(): void {
    // Retrieves subject ID from theme card properties
    this.subjectId = this.themeCardProps.subjectId;

    // Retrieves current user information
    this.user$ = this.sessionService.$getUser().pipe(
      map((user) => {
        this.isError = false;
        return user;
      }),
      catchError((error: any) => {
        console.error(error);
        this.isError = true;
        return of(undefined);
      })
    );
  }

  /**
   * Subscribes the user to the subject associated with the theme card.
   */
  subscribeToSubject() {
    this.userService
      .subscribe(this.subjectId)
      .pipe(
        // Resets error flag and updates user data on successful subscription
        map((user: User) => {
          this.isError = false;
          this.sessionService.updateUser(user);
        }),
        catchError((error) => {
          console.error(error);
          this.isError = true;
          return of(null);
        })
      )
      .subscribe();
  }

  /**
   * Unsubscribes the user from the subject associated with the theme card.
   */
  unsubscribeFromSubject() {
    this.userService
      .unsubscribe(this.subjectId)
      .pipe(
        // Resets error flag and updates user data on successful unsubscription
        map((user: User) => {
          this.isError = false;
          this.sessionService.updateUser(user);
        }),
        catchError((error) => {
          console.error(error);
          this.isError = true;
          return of(null);
        })
      )
      .subscribe();
  }
}
