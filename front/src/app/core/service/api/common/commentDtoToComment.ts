import { Comment } from '../../../model/Comment.model';
import { CommentResponse } from '../interface/comment/response/CommentResponse';

const commentDtoToComment = (commentDto: CommentResponse): Comment => {
  const { id, content, authorId, createdAt, postId } = commentDto;
  return new Comment(id, content, authorId, postId, createdAt);
};

export default commentDtoToComment;
