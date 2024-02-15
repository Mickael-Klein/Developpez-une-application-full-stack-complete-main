import { Comment } from '../../../model/Comment.model';
import { CommentResponse } from '../interface/comment/response/CommentResponse';

const commentDtoToComment = (commentDto: CommentResponse): Comment => {
  const { id, content, authorId, authorName, createdAt, postId } = commentDto;
  return new Comment(id, content, authorId, authorName, postId, createdAt);
};

export default commentDtoToComment;
