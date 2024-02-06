import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, catchError, map } from 'rxjs';
import { Subject } from '../../model/Subject.model';
import { environment } from '../../../enviroment/environment';
import getErrorMessageFromCatchedError from './common/errorResponse';
import { SubjectResponse } from './interface/subject/SubjectResponse';

@Injectable({
  providedIn: 'root',
})
export class SubjectService {
  private pathService = 'subject/';
  constructor(private http: HttpClient) {}

  public getAllSubjects(): Observable<Subject[]> {
    return this.http
      .get<SubjectResponse[]>(environment.apiUrl + this.pathService + 'getall')
      .pipe(
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
      .get<SubjectResponse>(
        environment.apiUrl + this.pathService + 'getbyid/' + subjectId
      )
      .pipe(
        map((response: SubjectResponse) =>
          this.getSubjectFromSubjectResponse(response)
        ),
        catchError((error: any) => getErrorMessageFromCatchedError(error))
      );
  }

  public getSubjectWithPostById(subjectId: number): Observable<Subject> {
    return this.http
      .get<SubjectResponse>(
        environment.apiUrl + this.pathService + 'getbyidwithpost/' + subjectId
      )
      .pipe(
        map((response: SubjectResponse) =>
          this.getSubjectFromSubjectResponse(response)
        ),
        catchError((error: any) => getErrorMessageFromCatchedError(error))
      );
  }

  private getSubjectFromSubjectResponse(response: SubjectResponse): Subject {
    if (response.postIds && response.postIds.length > 0) {
      const { id, name, description, postIds } = response;
      return new Subject(id, name, description, postIds);
    } else {
      const { id, name, description } = response;
      return new Subject(id, name, description);
    }
  }
}
