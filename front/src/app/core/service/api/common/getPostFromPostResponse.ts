import { Comment } from '../../../model/Comment.model';
import { Post } from '../../../model/Post.model';
import { CommentResponse } from '../interface/comment/response/CommentResponse';
import { PostResponse } from '../interface/post/response/PostResponse';
import commentDtoToComment from './commentDtoToComment';

/**
 * Converts a PostResponse object to a Post object.
 * @param postResponse The PostResponse object to convert.
 * @returns The converted Post object.
 */
const getPostFromPostResponse = (postResponse: PostResponse): Post => {
  if (postResponse.commentDtos) {
    const {
      id,
      title,
      content,
      authorId,
      authorName,
      subjectId,
      subjectName,
      createdAt,
      commentDtos,
    } = postResponse;

    const commentsArr: Comment[] = commentDtos.map(
      (commentDto: CommentResponse) => commentDtoToComment(commentDto)
    );

    // Return a new Post object with comments
    return new Post(
      id,
      title,
      content,
      authorId,
      authorName,
      subjectId,
      subjectName,
      createdAt,
      commentsArr
    );
  } else {
    const {
      id,
      title,
      content,
      authorId,
      authorName,
      subjectId,
      subjectName,
      createdAt,
    } = postResponse;

    // Return a new Post object without comments
    return new Post(
      id,
      title,
      content,
      authorId,
      authorName,
      subjectId,
      subjectName,
      createdAt
    );
  }
};

export default getPostFromPostResponse;
