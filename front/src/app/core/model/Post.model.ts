import { Comment } from './Comment.model';

export class Post {
  constructor(
    public id: number,
    public title: string,
    public content: string,
    public authorId: number,
    public authorName: string,
    public subjectId: number,
    public createdAt: Date,
    public comments?: Comment[]
  ) {}
}
