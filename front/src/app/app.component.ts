import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterOutlet } from '@angular/router';
import { SessionService } from './core/service/session/session.service';
import { HeaderComponent } from './component/header/header.component';
import { SubjectService } from './core/service/api/subject.service';
import { Subject } from './core/model/Subject.model';

/**
 * Main Component of the MDD Client App.
 * @class
 */
@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule, RouterOutlet, HeaderComponent],
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'],
})
export class AppComponent implements OnInit {
  sessionLoginWithJwtIsLoading = true;

  constructor(
    private sessionService: SessionService,
    private subjectService: SubjectService
  ) {}

  ngOnInit(): void {
    // Attempt login with JWT stored in local storage (service will check if there is any and handle logic case success)
    this.sessionService.loginWithLocalStorageJwt();

    this.sessionService.$isLoading().subscribe((isLoading: boolean) => {
      this.sessionLoginWithJwtIsLoading = isLoading;
    });

    this.sessionService.$isLogged().subscribe((isLogged: boolean) => {
      // Fetch subjects only if user is logged in to avoid unauthorized access
      if (isLogged) {
        this.subjectService.getAllSubjects().subscribe({
          next: (subjects: Subject[]) => {
            this.subjectService.subjects = subjects;
            console.log(this.subjectService.subjects);
          },
          error: (error: any) => {
            console.log('error while fetching subjects');
          },
        });
      }
    });
  }
}
