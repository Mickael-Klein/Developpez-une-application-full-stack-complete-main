import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { PostService } from '../../core/service/api/post.service';
import { Post } from '../../core/model/Post.model';
import { CommonModule } from '@angular/common';
import { Observable, catchError, throwError } from 'rxjs';
import { CommentComponent } from '../../component/comment/comment.component';

@Component({
  selector: 'app-article',
  standalone: true,
  imports: [CommonModule, CommentComponent],
  templateUrl: './article.component.html',
  styleUrl: './article.component.scss',
})
export class ArticleComponent implements OnInit {
  post$!: Observable<Post>;
  errorOnIdParameter!: boolean;
  isLoading = true;

  constructor(
    private router: Router,
    private postService: PostService,
    private activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    const postId = this.activatedRoute.snapshot.paramMap.get('id');

    if (!postId) {
      this.errorOnIdParameter = true;
    } else {
      const postIdToInt = parseInt(postId);

      if (Number.isNaN(postIdToInt)) {
        this.errorOnIdParameter = true;
      } else {
        this.post$ = this.postService.getById(postIdToInt).pipe(
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
  }

  backToArticles() {
    this.router.navigateByUrl('/articles');
  }
}
