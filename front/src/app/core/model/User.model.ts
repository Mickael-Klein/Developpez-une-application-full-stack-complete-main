import { Subject } from './Subject.model';

export class User {
  constructor(
    public id: number,
    public email: string,
    public username: string,
    public subjectIds?: number[]
  ) {}
}
