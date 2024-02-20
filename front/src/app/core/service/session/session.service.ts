import { Injectable } from '@angular/core';
import { User } from '../../model/User.model';
import { BehaviorSubject, Observable, catchError, map, of, tap } from 'rxjs';
import { UserService } from '../api/user.service';
import { UserResponse } from '../api/interface/user/response/UserResponse';

@Injectable({
  providedIn: 'root',
})
export class SessionService {
  public isLogged = false;
  public user: User | undefined = undefined;
  public jwt: String | null = null;
  public isLoading: boolean = true;

  private isLoggedSubject = new BehaviorSubject<boolean>(this.isLogged);
  private isLoadingSubject = new BehaviorSubject<boolean>(this.isLoading);

  constructor(private userService: UserService) {}

  public $isLogged(): Observable<boolean> {
    return this.isLoggedSubject.asObservable();
  }

  public $isLoading(): Observable<boolean> {
    return this.isLoadingSubject.asObservable();
  }

  public loginWithLocalStorageJwt(): void {
    const jwt = localStorage.getItem('jwt');
    if (jwt !== null) {
      this.userService
        .getMeWithSub()
        .pipe(
          tap((user: UserResponse) => {
            this.user = user;
            this.isLogged = true;
            this.jwt = jwt;
            this.isLoading = false;
            this.next();
            this.isLoadingSubject.next(this.isLoading);
          }),
          catchError((error) => {
            this.isLogged = false;
            this.user = undefined;
            this.jwt = null;
            localStorage.removeItem('jwt');
            this.isLoading = false;
            this.next();
            this.isLoadingSubject.next(this.isLoading);
            return [];
          })
        )
        .subscribe();
    } else {
      this.isLoading = false;
      this.isLoadingSubject.next(this.isLoading);
    }
  }

  public logIn(jwt: string): Observable<boolean> {
    const localStorageJwt = localStorage.getItem('jwt');
    if (localStorageJwt === null || localStorageJwt !== jwt) {
      localStorage.setItem('jwt', jwt);
    }
    return this.userService.getMeWithSub().pipe(
      map((user: UserResponse) => {
        this.user = user;
        this.isLogged = true;
        this.jwt = jwt;
        this.next();
        console.log('user', this.user);
        return true;
      }),
      catchError((error) => {
        this.isLogged = false;
        this.user = undefined;
        this.jwt = null;
        localStorage.removeItem('jwt');
        this.next();
        return of(false);
      })
    );
  }

  public logOut() {
    this.isLogged = false;
    this.user = undefined;
    this.jwt = null;
    localStorage.removeItem('jwt');
    this.next();
  }

  public setIsLoading(value: boolean) {
    this.isLoading = value;
  }

  private next(): void {
    this.isLoggedSubject.next(this.isLogged);
  }
}
