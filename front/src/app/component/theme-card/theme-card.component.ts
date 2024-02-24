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
    this.subjectId = this.themeCardProps.subjectId;
    this.user$ = this.sessionService.$getUser().pipe(
      map((user) => {
        this.isError = false;
        return user;
      }),
      catchError((error: any) => {
        console.log(error);
        this.isError = true;
        return of(undefined);
      })
    );
  }

  subscribeToSubject() {
    this.userService
      .subscribe(this.subjectId)
      .pipe(
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

  unsubscribeFromSubject() {
    this.userService
      .unsubscribe(this.subjectId)
      .pipe(
        map((user: User) => {
          this.isError = false;
          this.sessionService.updateUser(user);
        }),
        catchError((error) => {
          console.log(error);
          this.isError = true;
          return of(null);
        })
      )
      .subscribe();
  }
}
