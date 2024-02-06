import { Comment } from '../../../model/Comment.model';
import { Post } from '../../../model/Post.model';
import { CommentResponse } from '../interface/comment/response/CommentResponse';
import { PostResponse } from '../interface/post/response/PostResponse';
import commentDtoToComment from './commentDtoToComment';

const getPostFromPostResponse = (postResponse: PostResponse): Post => {
  if (
    postResponse.commentDtos !== undefined &&
    postResponse.commentDtos.length > 0
  ) {
    const { id, title, content, authorId, subjectId, createdAt, commentDtos } =
      postResponse;
    const commentsArr: Comment[] = commentDtos.map(
      (commentDto: CommentResponse) => commentDtoToComment(commentDto)
    );
    return new Post(
      id,
      title,
      content,
      authorId,
      subjectId,
      createdAt,
      commentsArr
    );
  } else {
    const { id, title, content, authorId, subjectId, createdAt } = postResponse;
    return new Post(id, title, content, authorId, subjectId, createdAt);
  }
};

export default getPostFromPostResponse;
