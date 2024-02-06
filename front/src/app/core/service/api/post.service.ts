import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { CreatePostRequest } from './interface/post/request/CreatePostRequest';
import { Observable, catchError, map } from 'rxjs';
import { PostResponse } from './interface/post/response/PostResponse';
import { environment } from '../../../enviroment/environment';
import getErrorMessageFromCatchedError from './common/errorResponse';
import { Post } from '../../model/Post.model';
import getPostFromPostResponse from './common/getPostFromPostResponse';

@Injectable({
  providedIn: 'root',
})
export class PostService {
  private pathService = 'post/';
  constructor(private http: HttpClient) {}

  public create(createPostRequest: CreatePostRequest): Observable<Post> {
    return this.http
      .post<PostResponse>(
        environment.apiUrl + this.pathService + 'create',
        createPostRequest
      )
      .pipe(
        map((response: PostResponse) => getPostFromPostResponse(response)),
        catchError((error: any) => getErrorMessageFromCatchedError(error))
      );
  }

  public getById(postId: number): Observable<Post> {
    return this.http
      .get<PostResponse>(
        environment.apiUrl + this.pathService + 'getpost/' + postId
      )
      .pipe(
        map((response: PostResponse) => getPostFromPostResponse(response)),
        catchError((error: any) => getErrorMessageFromCatchedError(error))
      );
  }

  public getByIdWithComments(postId: number): Observable<Post> {
    return this.http
      .get<PostResponse>(
        environment.apiUrl + this.pathService + 'getpostwithcomments/' + postId
      )
      .pipe(
        map((response: PostResponse) => getPostFromPostResponse(response)),
        catchError((error: any) => getErrorMessageFromCatchedError(error))
      );
  }
}
