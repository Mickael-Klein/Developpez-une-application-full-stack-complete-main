import { Comment } from '../../../model/Comment.model';
import { CommentResponse } from '../interface/comment/response/CommentResponse';

/**
 * Converts a comment DTO (Data Transfer Object) to a Comment model.
 * @param commentDto The comment DTO to convert.
 * @returns The converted Comment model.
 */
const commentDtoToComment = (commentDto: CommentResponse): Comment => {
  const { id, content, authorId, authorName, createdAt, postId } = commentDto;
  return new Comment(id, content, authorId, authorName, postId, createdAt);
};

export default commentDtoToComment;
