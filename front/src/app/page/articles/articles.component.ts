import { Component, OnDestroy, OnInit } from '@angular/core';
import { SessionService } from '../../core/service/session/session.service';
import { SubjectService } from '../../core/service/api/subject.service';
import { Router } from '@angular/router';
import { Subject } from '../../core/model/Subject.model';
import { Post } from '../../core/model/Post.model';
import { Observable, Subscription, filter, from, map, mergeMap, of, switchMap } from 'rxjs';
import { ButtonComponent } from '../../component/button/button.component';
import { Button } from '../../interface/Button.interface';
import { PostCardComponent } from '../../component/post-card/post-card.component';
import { CommonModule } from '@angular/common';

/**
 * Component for displaying articles page.
 * @class
 */
@Component({
  selector: 'app-articles',
  standalone: true,
  imports: [ButtonComponent, PostCardComponent, CommonModule],
  templateUrl: './articles.component.html',
  styleUrl: './articles.component.scss',
})
export class ArticlesComponent implements OnInit, OnDestroy {
  subscription$!: Observable<number[]>;
  subjects: Subject[] = [];
  orderedPostsByDate: Post[] = [];
  isLoading = true;
  error = false;
  subjectNumber = 0;
  subjectCount = 0;

  buttonProps: Button = {
    text: 'Créer un article',
    colored: true,
  };

  subscription!: Subscription;

  constructor(
    private sessionService: SessionService,
    private subjectService: SubjectService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.isLoading = true;

    // Fetches user subscription data
    this.subscription$ = this.sessionService.$getUser().pipe(
      filter((user) => user !== undefined), // Filters out undefined user objects
      map((user) => {
        if (user?.subjectIds) {
          return user.subjectIds; // Extracts subject IDs from user data
        } else {
          return [];
        }
      })
    );

    // Retrieves subjects with posts based on user subscription
    const subjects$ = this.subscription$.pipe(
      switchMap((subscription) => {
        // If user is subscribed to any subjects
        if (subscription.length > 0) {
          this.subjectNumber = subscription.length;
          // Convert subscription array to an Observable and use mergeMap
          // to fetch subjects concurrently based on each subscription ID
          return from(subscription).pipe(
            mergeMap((subId) =>
              this.subjectService.getSubjectWithPostById(subId)
            )
          );
        } else {
          // If user is not subscribed to any subjects
          // Fetch all subjects and their posts
          return this.subjectService.$getSubjects().pipe(
            switchMap((subjects) => {
              if (subjects && subjects.length > 0) {
                this.subjectNumber = subjects.length;
                // Convert subjects array to an Observable and use mergeMap
                // to fetch posts concurrently for each subject
                return from(subjects).pipe(
                  mergeMap((subject: Subject) =>
                    this.subjectService.getSubjectWithPostById(subject.id)
                  )
                );
              } else {
                return of();
              }
            })
          );
        }
      })
    );

    // Subscribes to the final stream of subjects with posts
    this.subscription = subjects$.subscribe({
      next: (subject: Subject) => {
        this.subjectCount++;

        // Extract and accumulate posts from the subject
        if (subject.post) {
          subject.post.forEach((post: Post) => {
            this.orderedPostsByDate.push(post);
          });
        }
        if (this.subjectCount === this.subjectNumber) {
          // if all requested subjects had been fetch, end loading state (complete)
          this.orderedPostsByDate = this.orderPostArrByDateDesc(); // Orders posts by date
          console.log(this.orderedPostsByDate);
          this.isLoading = false;
        }
      },
      error: (error: any) => {
        console.log(error);
        this.error = true; // Sets error flag on error
        this.isLoading = false;
      },
    });
  }

  ngOnDestroy(): void {
      if(this.subscription) {
        this.subscription.unsubscribe();
      }
  }

  // Orders posts by date in descending order
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
