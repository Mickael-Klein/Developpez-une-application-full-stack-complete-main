import { Component, OnInit } from '@angular/core';
import { SessionService } from '../../core/service/session/session.service';
import { SubjectService } from '../../core/service/api/subject.service';
import { Router } from '@angular/router';
import { Subject } from '../../core/model/Subject.model';
import { Post } from '../../core/model/Post.model';
import { from, mergeMap } from 'rxjs';
import { ButtonComponent } from '../../component/button/button.component';
import { Button } from '../../interface/Button.interface';
import { PostCardComponent } from '../../component/post-card/post-card.component';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-articles',
  standalone: true,
  imports: [ButtonComponent, PostCardComponent, CommonModule],
  templateUrl: './articles.component.html',
  styleUrl: './articles.component.scss',
})
export class ArticlesComponent implements OnInit {
  subscription: number[] = [];
  subjects: Subject[] = [];
  orderedPostsByDate: Post[] = [];
  isLoading = true;
  error = false;

  buttonProps: Button = {
    text: 'Créer un article',
    colored: true,
  };

  constructor(
    private sessionService: SessionService,
    private subjectService: SubjectService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.isLoading = true;

    this.subscription = this.sessionService.user?.subjectIds || [];

    const subjects$ =
      this.subscription.length > 0
        ? from(this.subscription).pipe(
            mergeMap((subId) =>
              this.subjectService.getSubjectWithPostById(subId)
            )
          )
        : from(this.subjectService.subjects).pipe(
            mergeMap((subject: Subject) =>
              this.subjectService.getSubjectWithPostById(subject.id)
            )
          );

    subjects$.subscribe({
      next: (subject: Subject) => {
        if (subject.post) {
          subject.post.forEach((post: Post) => {
            this.orderedPostsByDate.push(post);
          });
        }
      },
      error: (error: any) => {
        console.log(error);
        this.error = true;
        this.isLoading = false;
      },
      complete: () => {
        this.orderedPostsByDate = this.orderPostArrByDateDesc();
        console.log(this.orderedPostsByDate);
        this.isLoading = false;
      },
    });
  }

  private orderPostArrByDateDesc(): Post[] {
    return this.orderedPostsByDate.sort((a: Post, b: Post) => {
      const dateA = new Date(a.createdAt).getTime();
      const dateB = new Date(b.createdAt).getTime();
      return dateB - dateA;
    });
  }

  onPostCreateButtonClick() {
    this.router.navigateByUrl('/new-article');
  }
}
