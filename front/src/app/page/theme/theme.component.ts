import { Component, OnInit } from '@angular/core';
import { SubjectService } from '../../core/service/api/subject.service';
import { Observable, catchError, map } from 'rxjs';
import { Subject } from '../../core/model/Subject.model';
import { CommonModule } from '@angular/common';
import { ThemeCardComponent } from '../../component/theme-card/theme-card.component';
import { ThemeCard } from '../../interface/ThemeCard.interface';

/**
 * Component for displaying theme page.
 * This component allows users to see all subjects/themes available and subscribe to them.
 * @class
 */
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
    // Retrieve list of subjects and handle errors
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

  /**
   * Transforms a subject into theme card properties.
   * @param subject - The subject object
   * @returns themeCard - Theme card properties
   */
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
