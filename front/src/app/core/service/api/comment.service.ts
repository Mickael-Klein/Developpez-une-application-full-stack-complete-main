import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { CreateCommentRequest } from './interface/comment/request/CreateCommentRequest';
import { Observable, catchError, map } from 'rxjs';
import { Post } from '../../model/Post.model';
import { PostResponse } from './interface/post/response/PostResponse';
import getErrorMessageFromCatchedError from './common/errorResponse';
import getPostFromPostResponse from './common/getPostFromPostResponse';

@Injectable({
  providedIn: 'root',
})
export class CommentService {
  private pathService = 'api/comment/';
  constructor(private http: HttpClient) {}

  public createComment(commentRequest: CreateCommentRequest): Observable<Post> {
    return this.http
      .post<PostResponse>(this.pathService + 'create', commentRequest)
      .pipe(
        map((response: PostResponse) => getPostFromPostResponse(response)),
        catchError((error: any) => getErrorMessageFromCatchedError(error))
      );
  }
}
