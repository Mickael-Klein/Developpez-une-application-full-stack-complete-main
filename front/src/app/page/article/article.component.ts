import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { PostService } from '../../core/service/api/post.service';
import { Post } from '../../core/model/Post.model';
import { CommonModule } from '@angular/common';
import { Observable, catchError, map, of, throwError } from 'rxjs';
import { CommentComponent } from '../../component/comment/comment.component';
import {
  FormBuilder,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { CommentService } from '../../core/service/api/comment.service';
import { CreateCommentRequest } from '../../core/service/api/interface/comment/request/CreateCommentRequest';
import { PostResponse } from '../../core/service/api/interface/post/response/PostResponse';

/**
 * ArticleComponent class representing the component for displaying the article page.
 * @class
 */
@Component({
  selector: 'app-article',
  standalone: true,
  imports: [CommonModule, CommentComponent, ReactiveFormsModule],
  templateUrl: './article.component.html',
  styleUrl: './article.component.scss',
})
export class ArticleComponent implements OnInit {
  post$!: Observable<Post>;
  errorOnIdParameter!: boolean;
  isLoading = true;
  commentForm!: FormGroup;
  commentHasError = false;
  postId!: string | null;
  postIdToInt!: number;
  commentServiceError = false;

  constructor(
    private router: Router,
    private postService: PostService,
    private commentService: CommentService,
    private activatedRoute: ActivatedRoute,
    private formBuilder: FormBuilder
  ) {}

  ngOnInit(): void {
    // get id url parameter and fetch post with id, assign to post$ if success or trigger error handling logic
    this.postId = this.activatedRoute.snapshot.paramMap.get('id');

    if (!this.postId) {
      this.errorOnIdParameter = true;
    } else {
      this.postIdToInt = parseInt(this.postId);

      if (Number.isNaN(this.postIdToInt)) {
        this.errorOnIdParameter = true;
      } else {
        this.post$ = this.postService.getById(this.postIdToInt).pipe(
          catchError((error) => {
            console.error(
              'Erreur lors de la récupération des données du post :',
              error
            );
            this.errorOnIdParameter = true;
            this.isLoading = false;
            return throwError(() => error);
          })
        );
        this.isLoading = false;
      }
    }

    this.commentForm = this.formBuilder.group({
      comment: [
        '',
        [
          Validators.required,
          Validators.pattern(/^(?=.*[a-zA-Z0-9!@#$%^&*()-_+=])[\s\S]*$/),
        ],
      ],
    });
  }

  backToArticles() {
    this.router.navigateByUrl('/articles');
  }

  /**
   * Submits a comment.
   * Validates comment content before submission.
   * If content is valid, creates a comment using the comment service.
   */
  submitComment() {
    this.commentHasError = false;
    this.commentServiceError = false;

    const commentControl = this.commentForm.controls['comment'];

    const commentContent = commentControl.value;
    const isCommentContentValid = commentControl.valid;

    // If the comment content is not valid, set error flag and return
    if (!isCommentContentValid) {
      this.commentHasError = true;
      return;
    }

    const commentRequest: CreateCommentRequest = {
      postId: this.postIdToInt,
      content: commentContent,
    };

    this.commentService.createComment(commentRequest).subscribe({
      next: (response: PostResponse) => {
        // Update the post with the response (updated post with comments)
        this.post$ = of(response);
        this.commentForm.get('comment')?.setValue('');
      },
      error: (error: any) => {
        console.log(error);
        // Set error flag for the comment service
        this.commentServiceError = true;
      },
    });
  }
}
