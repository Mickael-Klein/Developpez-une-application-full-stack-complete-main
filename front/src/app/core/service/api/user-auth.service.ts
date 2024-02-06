import { HttpClient, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, map } from 'rxjs';
import { RegisterRequest } from './interface/userAuth/request/RegisterRequest';
import { environment } from '../../../enviroment/environment';
import { LoginRequest } from './interface/userAuth/request/LoginRequest';
import { LoginResponse } from './interface/userAuth/response/LoginResponse';
import { RegisterResponse } from './interface/userAuth/response/RegisterResponse';

@Injectable({
  providedIn: 'root',
})
export class UserAuthService {
  private pathService = 'auth/';

  constructor(private http: HttpClient) {}

  public register(registerRequest: RegisterRequest): Observable<boolean> {
    return this.http
      .post<HttpResponse<RegisterResponse>>(
        environment.apiUrl + this.pathService + 'register',
        registerRequest
      )
      .pipe(
        map((response: HttpResponse<RegisterResponse>) => {
          if (response.ok) {
            return response.ok;
          } else {
            let errorMessage = 'an error occured';
            if (response.body?.message) {
              errorMessage = response.body.message;
            }
            throw new Error(errorMessage);
          }
        })
      );
  }

  public login(loginRequest: LoginRequest): Observable<string> {
    return this.http
      .post<LoginResponse>(
        environment.apiUrl + this.pathService + 'login',
        loginRequest
      )
      .pipe(
        map((response: LoginResponse) => {
          if (response.token) {
            return response.token;
          } else {
            throw new Error(response.message);
          }
        })
      );
  }
}
