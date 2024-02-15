export class Comment {
  constructor(
    public id: number,
    public content: string,
    public authorId: number,
    public authorName: string,
    public postId: number,
    public createdAt: Date
  ) {}
}
