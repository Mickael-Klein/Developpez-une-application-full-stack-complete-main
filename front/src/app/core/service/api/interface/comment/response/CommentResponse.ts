export interface CommentResponse {
  id: number;
  content: string;
  authorId: number;
  authorName: string;
  createdAt: Date;
  postId: number;
}
