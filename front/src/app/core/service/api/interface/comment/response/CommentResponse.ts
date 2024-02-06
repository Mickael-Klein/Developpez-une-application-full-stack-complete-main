export interface CommentResponse {
  id: number;
  content: string;
  authorId: number;
  createdAt: Date;
  postId: number;
}
