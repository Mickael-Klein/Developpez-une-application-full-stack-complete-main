import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterOutlet } from '@angular/router';
import { SessionService } from './core/service/session/session.service';
import { HeaderComponent } from './component/header/header.component';
import { SubjectService } from './core/service/api/subject.service';
import { Subject } from './core/model/Subject.model';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule, RouterOutlet, HeaderComponent],
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'],
})
export class AppComponent implements OnInit {
  private noAuthRoutes = ['/login', '/register', '/home', '/'];
  isLoading = true;

  constructor(
    private router: Router,
    private sessionService: SessionService,
    private subjectService: SubjectService
  ) {}

  ngOnInit(): void {
    this.loadInitialAuthState();

    this.sessionService.$isLogged().subscribe((isLogged: boolean) => {
      let isOnNoAuthRoute = false;
      const currentUrl = this.router.url;
      this.noAuthRoutes.forEach((route) => {
        if (route === currentUrl) {
          isOnNoAuthRoute = true;
        }
      });
      if (isLogged && isOnNoAuthRoute) {
        this.router.navigateByUrl('/articles');
      } else if (!isLogged && !isOnNoAuthRoute) {
        this.router.navigateByUrl('/login');
      }

      // Subjects fetch only if user is logged to avoid access to unauthorized data in network xhr request
      if (isLogged) {
        this.subjectService.getAllSubjects().subscribe({
          next: (subjects: Subject[]) => {
            this.subjectService.subjects = subjects;
          },
          error: (error: any) => {
            console.log('error while fetching subjects');
          },
        });
      }
    });
  }

  private loadInitialAuthState(): void {
    if (!localStorage.getItem('jwt')) {
      this.isLoading = false;
    } else {
      this.sessionService.loginWithLocalStorageJwt();
      this.isLoading = false;
    }
  }
}
