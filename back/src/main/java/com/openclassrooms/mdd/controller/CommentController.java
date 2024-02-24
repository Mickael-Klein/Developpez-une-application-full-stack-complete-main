package com.openclassrooms.mdd.controller;

import com.openclassrooms.mdd.model.Comment;
import com.openclassrooms.mdd.model.DbUser;
import com.openclassrooms.mdd.model.Post;
import com.openclassrooms.mdd.service.JwtServiceImpl;
import com.openclassrooms.mdd.serviceInterface.CommentInterface;
import com.openclassrooms.mdd.serviceInterface.DbUserInterface;
import com.openclassrooms.mdd.serviceInterface.PostInterface;
import com.openclassrooms.mdd.util.entityAndDtoCreation.EntityAndDtoCreation;
import com.openclassrooms.mdd.util.payload.request.CommentRequest;
import com.openclassrooms.mdd.util.payload.response.DbUserResponse;
import com.openclassrooms.mdd.util.payload.response.PostResponse;
import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for handling operations related to comments.
 */
@RestController
@RequestMapping("/api/comment")
public class CommentController {

  @Autowired
  private CommentInterface commentService;

  @Autowired
  private JwtServiceImpl jwtService;

  @Autowired
  private EntityAndDtoCreation entityAndDtoCreation;

  @Autowired
  private DbUserInterface dbUserService;

  @Autowired
  private DbUserResponse dbUserResponse;

  @Autowired
  private PostInterface postService;

  @Autowired
  private PostResponse postResponse;

  /**
   * Endpoint for creating a new comment.
   * @param jwt The JWT token obtained from the request.
   * @param commentRequest The request payload containing the comment data.
   * @return ResponseEntity containing the response to the request.
   */
  @PostMapping("/create")
  public ResponseEntity<?> createComment(
    @AuthenticationPrincipal Jwt jwt,
    @Valid @RequestBody CommentRequest commentRequest
  ) {
    try {
      // Retrieve the post based on the provided post ID
      Optional<Post> optionalPost = postService.getPostById(
        commentRequest.getPostId()
      );
      if (!optionalPost.isPresent()) {
        return ResponseEntity
          .badRequest()
          .body(postResponse.getPostInvalidPostId());
      }
      Post post = optionalPost.get();

      // Retrieve the user based on the JWT token
      long userId = jwtService.getUserIdFromJwtLong(jwt);
      Optional<DbUser> optionalDbUser = dbUserService.getUserById(userId);
      if (!optionalDbUser.isPresent()) {
        return ResponseEntity
          .badRequest()
          .body(dbUserResponse.getMeNoUserFoundWithThisJwtTokenId());
      }
      DbUser dbUser = optionalDbUser.get();

      // Create the comment
      Comment comment = new Comment();
      comment.setContent(commentRequest.getContent());
      comment.setDbUser(dbUser);
      comment.setPost(post);
      comment.setCreatedAt(LocalDateTime.now());

      // Save the comment
      Comment savedComment = commentService.saveComment(comment);

      // Retrieve the updated post with comments
      List<Comment> commentList = post.getComments();

      // Return the response with the updated post
      return ResponseEntity
        .ok()
        .body(
          entityAndDtoCreation.getPostWithCommentsDtoFromPostWithCommentEntity(
            post
          )
        );
    } catch (Exception e) {
      // Return an internal server error response if an exception occurs
      return ResponseEntity.internalServerError().build();
    }
  }
}
