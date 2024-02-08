import { Injectable } from '@angular/core';
import { User } from '../../model/User.model';
import { BehaviorSubject, catchError, tap } from 'rxjs';
import { UserService } from '../api/user.service';

@Injectable({
  providedIn: 'root',
})
export class SessionService {
  public isLogged = false;
  public user: User | undefined = undefined;
  public jwt: String | null = null;

  private isLoggedSubject = new BehaviorSubject<boolean>(this.isLogged);

  constructor(private userService: UserService) {}

  public loginWithLocalStorageJwt(): void {
    const jwt = localStorage.getItem('jwt');
    if (jwt !== null) {
      this.userService
        .getMe()
        .pipe(
          tap((user) => {
            this.user = user;
            this.isLogged = true;
            this.jwt = jwt;
            this.next();
          }),
          catchError((error) => {
            this.isLogged = false;
            this.user = undefined;
            this.jwt = null;
            localStorage.removeItem('jwt');
            this.next();
            return [];
          })
        )
        .subscribe();
    }
  }

  public logIn(user: User, jwt: string): void {
    this.isLogged = true;
    this.user = user;
    localStorage.setItem('jwt', jwt);
    this.jwt = jwt;
    this.next();
  }

  public logout() {
    this.isLogged = false;
    this.user = undefined;
    this.jwt = null;
    localStorage.removeItem('jwt');
    this.next();
  }

  private next(): void {
    this.isLoggedSubject.next(this.isLogged);
  }
}
