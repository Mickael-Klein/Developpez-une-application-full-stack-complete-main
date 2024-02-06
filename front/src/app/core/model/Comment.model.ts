export class Comment {
  constructor(
    public id: number,
    public content: string,
    public authorId: number,
    public postId: number,
    public createdAt: Date
  ) {}
}
