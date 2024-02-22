import { Component, OnInit } from '@angular/core';
import { SessionService } from '../../core/service/session/session.service';
import { Router } from '@angular/router';
import { SubjectService } from '../../core/service/api/subject.service';
import { Observable, catchError, filter, map, of, tap } from 'rxjs';
import { Subject } from '../../core/model/Subject.model';
import { User } from '../../core/model/User.model';
import { CommonModule } from '@angular/common';
import { ThemeCardComponent } from '../../component/theme-card/theme-card.component';
import { ThemeCard } from '../../interface/ThemeCard.interface';

@Component({
  selector: 'app-theme',
  standalone: true,
  imports: [CommonModule, ThemeCardComponent],
  templateUrl: './theme.component.html',
  styleUrl: './theme.component.scss',
})
export class ThemeComponent implements OnInit {
  subjectList$!: Observable<Subject[]>;
  subjectFetchError = false;

  constructor(private subjectService: SubjectService) {}

  ngOnInit(): void {
    this.subjectList$ = this.subjectService.$getSubjects().pipe(
      map((subjects) => {
        this.subjectFetchError = false;
        return subjects;
      }),
      catchError((error: any) => {
        this.subjectFetchError = true;
        return [];
      })
    );
  }

  toThemeCardProps(subject: Subject): ThemeCard {
    const themeCardProps: ThemeCard = {
      subjectId: subject.id,
      subjectName: subject.name,
      subjectContent: subject.description,
      isForUnsubscribe: false,
    };
    return themeCardProps;
  }
}
