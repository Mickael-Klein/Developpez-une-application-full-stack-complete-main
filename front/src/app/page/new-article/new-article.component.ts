import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { PostService } from '../../core/service/api/post.service';
import {
  FormBuilder,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { ButtonComponent } from '../../component/button/button.component';
import { Subject } from '../../core/model/Subject.model';
import { SubjectService } from '../../core/service/api/subject.service';
import { Button } from '../../interface/Button.interface';
import { SessionService } from '../../core/service/session/session.service';
import { CreatePostRequest } from '../../core/service/api/interface/post/request/CreatePostRequest';
import { PostResponse } from '../../core/service/api/interface/post/response/PostResponse';
import { CommonModule } from '@angular/common';
import { Observable, catchError, filter, map } from 'rxjs';

/**
 * Component for displaying new-article page.
 * This component allows users to create a new post/article.
 * @class
 */
@Component({
  selector: 'app-new-article',
  standalone: true,
  imports: [ReactiveFormsModule, ButtonComponent, CommonModule],
  templateUrl: './new-article.component.html',
  styleUrl: './new-article.component.scss',
})
export class NewArticleComponent implements OnInit {
  subjectList$!: Observable<Subject[]>;
  sujectFetchError = false;
  postForm!: FormGroup;
  themeHasError = false;
  themeIsNotPlaceholderSelection = false;
  titleHasError = false;
  contentHasError = false;
  submitHasError = false;
  submitSuccess = false;
  isSubmitting = false;

  buttonProps: Button = { colored: true, text: 'Créer' };

  constructor(
    private router: Router,
    private postService: PostService,
    private formBuilder: FormBuilder,
    private subjectService: SubjectService,
    private sessionService: SessionService
  ) {}

  ngOnInit(): void {
    this.subjectList$ = this.subjectService.$getSubjects().pipe(
      catchError((error: any) => {
        this.sujectFetchError = true;
        return [];
      })
    );

    this.postForm = this.formBuilder.group({
      theme: [null, [Validators.required]],
      title: [null, [Validators.required, Validators.maxLength(60)]],
      content: [null, [Validators.required, Validators.minLength(10)]],
    });
  }

  backToArticles() {
    this.router.navigateByUrl('/articles');
  }

  /** Handles the change event when selecting a theme */
  onSelectChange() {
    if (this.postForm.controls['theme'].value !== 'Sélectionner un thème') {
      this.themeIsNotPlaceholderSelection = true;
    }
  }

  /** Handles the form submission for creating a new post */
  onSubmitForm() {
    if (!this.isSubmitting) {
      this.isSubmitting = true;
      this.themeHasError = false;
      this.titleHasError = false;
      this.contentHasError = false;
      this.submitHasError = false;

      const themeControl = this.postForm.controls['theme'];
      const themeContent = themeControl.value;
      const isThemeValid = themeControl.valid;

      console.log('themeContent', themeContent);

      if (!isThemeValid) {
        this.themeHasError = true;
      }

      const titleControl = this.postForm.controls['title'];
      const titleContent = titleControl.value;
      const isTitleValid = titleControl.valid;

      console.log('titleContent', titleContent);

      if (!isTitleValid) {
        this.titleHasError = true;
      }

      const contentControl = this.postForm.controls['content'];
      const contentContent = contentControl.value;
      const isContentValid = contentControl.valid;

      console.log('contentContent', contentContent);

      if (!isContentValid) {
        this.contentHasError = true;
      }

      // If any error is present, stop submission
      if (this.themeHasError || this.titleHasError || this.contentHasError) {
        this.isSubmitting = false;
        return;
      }

      const subjectId = parseInt(themeContent);

      const newPost: CreatePostRequest = {
        title: titleContent,
        content: contentContent,
        subjectId: subjectId,
      };

      // Send request to create a new post
      this.postService.create(newPost).subscribe({
        next: (response: PostResponse) => {
          // Set success flag and navigate back to articles after 3 seconds
          this.submitSuccess = true;
          setTimeout(() => {
            this.router.navigateByUrl('/articles');
          }, 3000);
        },
        error: (error: any) => {
          console.log(error);
          this.submitHasError = true;
          this.isSubmitting = false;
        },
      });
    }
  }
}
