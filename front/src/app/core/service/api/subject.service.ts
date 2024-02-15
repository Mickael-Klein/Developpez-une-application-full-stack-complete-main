import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, catchError, map } from 'rxjs';
import { Subject } from '../../model/Subject.model';
import getErrorMessageFromCatchedError from './common/errorResponse';
import { SubjectResponse } from './interface/subject/SubjectResponse';

@Injectable({
  providedIn: 'root',
})
export class SubjectService {
  private pathService = 'api/subject/';
  subjects!: Subject[];

  constructor(private http: HttpClient) {}

  public getAllSubjects(): Observable<Subject[]> {
    return this.http.get<SubjectResponse[]>(this.pathService + 'getall').pipe(
      map((response: SubjectResponse[]) => {
        let subjectArr = response.map((subject: SubjectResponse) =>
          this.getSubjectFromSubjectResponse(subject)
        );
        return subjectArr;
      }),
      catchError((error: any) => getErrorMessageFromCatchedError(error))
    );
  }

  public getSubjectById(subjectId: number): Observable<Subject> {
    return this.http
      .get<SubjectResponse>(this.pathService + 'getbyid/' + subjectId)
      .pipe(
        map((response: SubjectResponse) =>
          this.getSubjectFromSubjectResponse(response)
        ),
        catchError((error: any) => getErrorMessageFromCatchedError(error))
      );
  }

  public getSubjectWithPostById(subjectId: number): Observable<Subject> {
    return this.http
      .get<SubjectResponse>(this.pathService + 'getbyidwithpost/' + subjectId)
      .pipe(
        map((response: SubjectResponse) =>
          this.getSubjectFromSubjectResponse(response)
        ),
        catchError((error: any) => getErrorMessageFromCatchedError(error))
      );
  }

  private getSubjectFromSubjectResponse(response: SubjectResponse): Subject {
    if (response.postDtos && response.postDtos.length > 0) {
      const { id, name, description, postDtos } = response;
      return new Subject(id, name, description, postDtos);
    } else {
      const { id, name, description } = response;
      return new Subject(id, name, description);
    }
  }
}
