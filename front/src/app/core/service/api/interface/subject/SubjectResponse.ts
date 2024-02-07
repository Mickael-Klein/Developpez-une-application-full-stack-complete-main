import { Post } from '../../../../model/Post.model';

export interface SubjectResponse {
  id: number;
  name: string;
  description: string;
  postDtos?: Post[];
}
