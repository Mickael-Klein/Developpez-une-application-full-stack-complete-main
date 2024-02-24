import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable, catchError, map } from 'rxjs';
import { Subject } from '../../model/Subject.model';
import getErrorMessageFromCatchedError from './common/errorResponse';
import { SubjectResponse } from './interface/subject/SubjectResponse';

/**
 * Service for managing subjects.
 */
@Injectable({
  providedIn: 'root',
})
export class SubjectService {
  private pathService = 'api/subject/';

  subjects!: Subject[];
  subjectSubject = new BehaviorSubject<Subject[]>(this.subjects);

  constructor(private http: HttpClient) {}

  /**
   * Gets an observable of subjects.
   * @returns An Observable of subjects.
   */
  public $getSubjects() {
    return this.subjectSubject.asObservable();
  }

  /**
   * Retrieves all subjects.
   * @returns An Observable of the retrieved subjects.
   */
  public getAllSubjects(): Observable<Subject[]> {
    // Sends a GET request to retrieve all subjects and handles the response
    return this.http.get<SubjectResponse[]>(this.pathService + 'getall').pipe(
      // Maps the response to an array of Subject objects
      map((response: SubjectResponse[]) => {
        let subjectArr = response.map((subject: SubjectResponse) =>
          this.getSubjectFromSubjectResponse(subject)
        );
        // Updates the local subjects array and notifies subscribers
        this.subjects = subjectArr;
        this.next();
        return subjectArr;
      }),
      // Handles errors by extracting error message and throwing an Error observable
      catchError((error: any) => getErrorMessageFromCatchedError(error))
    );
  }

  /**
   * Retrieves a subject by its ID.
   * @param subjectId - The ID of the subject to retrieve.
   * @returns An Observable of the retrieved subject.
   */
  public getSubjectById(subjectId: number): Observable<Subject> {
    // Sends a GET request to retrieve a subject by its ID and handles the response
    return this.http
      .get<SubjectResponse>(this.pathService + 'getbyid/' + subjectId)
      .pipe(
        // Maps the response to a Subject object
        map((response: SubjectResponse) =>
          this.getSubjectFromSubjectResponse(response)
        ),
        // Handles errors by extracting error message and throwing an Error observable
        catchError((error: any) => getErrorMessageFromCatchedError(error))
      );
  }

  /**
   * Retrieves a subject with posts by its ID.
   * @param subjectId - The ID of the subject to retrieve.
   * @returns An Observable of the retrieved subject with posts.
   */
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

  /**
   * Converts a SubjectResponse object to a Subject object.
   * @param response - The SubjectResponse object to convert.
   * @returns The converted Subject object.
   */
  private getSubjectFromSubjectResponse(response: SubjectResponse): Subject {
    // If the response contains postDtos, create a Subject object with posts
    if (response.postDtos && response.postDtos.length > 0) {
      const { id, name, description, postDtos } = response;
      return new Subject(id, name, description, postDtos);
    } else {
      // If the response does not contain postDtos, create a Subject object without posts
      const { id, name, description } = response;
      return new Subject(id, name, description);
    }
  }

  /**
   * Notifies subscribers about changes in subjects.
   */
  private next() {
    this.subjectSubject.next(this.subjects);
  }
}
