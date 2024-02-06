import { Comment } from '../../../../../model/Comment.model';

export interface PostResponse {
  id: number;
  title: string;
  content: string;
  authorId: number;
  subjectId: number;
  createdAt: Date;
  commentDtos?: Comment[];
}
