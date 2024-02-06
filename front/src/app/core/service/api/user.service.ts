import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, catchError, map } from 'rxjs';
import { User } from '../../model/User.model';
import { environment } from '../../../enviroment/environment';
import { UserResponse } from './interface/user/response/UserResponse';
import { UpdateUserRequest } from './interface/user/request/UpdateUserRequest';
import getErrorMessageFromCatchedError from './common/errorResponse';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  private pathService = 'user/';

  constructor(private http: HttpClient) {}

  public getMe(): Observable<User> {
    return this.http
      .get<UserResponse>(environment.apiUrl + this.pathService + 'me')
      .pipe(
        map((response: UserResponse) => this.getUserFromUserResponse(response)),
        catchError((error: any) => getErrorMessageFromCatchedError(error))
      );
  }

  public getMeWithSub(): Observable<User> {
    return this.http
      .get<UserResponse>(environment.apiUrl + this.pathService + 'mewithsub')
      .pipe(
        map((response: UserResponse) => this.getUserFromUserResponse(response)),
        catchError((error: any) => getErrorMessageFromCatchedError(error))
      );
  }

  public updateMe(updateUserRequest: UpdateUserRequest): Observable<User> {
    return this.http
      .put<UserResponse>(
        environment.apiUrl + this.pathService + 'updateme',
        updateUserRequest
      )
      .pipe(
        map((response: UserResponse) => this.getUserFromUserResponse(response)),
        catchError((error: any) => getErrorMessageFromCatchedError(error))
      );
  }

  public subscribe(subjectId: number): Observable<User> {
    return this.http
      .post<UserResponse>(
        environment.apiUrl + this.pathService + 'subscribe/' + subjectId,
        null
      )
      .pipe(
        map((response: UserResponse) => this.getUserFromUserResponse(response)),
        catchError((error: any) => getErrorMessageFromCatchedError(error))
      );
  }

  public unsubscribe(subjectId: number): Observable<User> {
    return this.http
      .delete<UserResponse>(
        environment.apiUrl + this.pathService + 'unsubscribe/' + subjectId
      )
      .pipe(
        map((response: UserResponse) => this.getUserFromUserResponse(response)),
        catchError((error: any) => getErrorMessageFromCatchedError(error))
      );
  }

  private getUserFromUserResponse(response: UserResponse): User {
    if (!response.subjectIds) {
      const { id, email, username } = response;
      return new User(id, email, username);
    } else {
      const { id, email, username, subjectIds } = response;
      return new User(id, email, username, subjectIds);
    }
  }
}
