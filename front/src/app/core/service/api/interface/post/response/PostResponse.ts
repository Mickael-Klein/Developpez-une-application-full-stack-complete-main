import { Comment } from '../../../../../model/Comment.model';

export interface PostResponse {
  id: number;
  title: string;
  content: string;
  authorId: number;
  authorName: string;
  subjectId: number;
  subjectName: string;
  createdAt: Date;
  commentDtos?: Comment[];
}
