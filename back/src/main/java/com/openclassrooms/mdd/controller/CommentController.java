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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
  private DbUserInterface dbUserservice;

  @Autowired
  private DbUserResponse dbUserResponse;

  @Autowired
  private PostInterface postService;

  @Autowired
  private PostResponse postResponse;

  @PostMapping("/create")
  public ResponseEntity<?> createComment(
    @AuthenticationPrincipal Jwt jwt,
    @Valid @RequestBody CommentRequest commentRequest
  ) {
    try {
      Optional<Post> optionalPost = postService.getPostById(
        commentRequest.getPostId()
      );
      if (!optionalPost.isPresent()) {
        return ResponseEntity
          .badRequest()
          .body(postResponse.getPostInvalidPostId());
      }
      Post post = optionalPost.get();

      long userId = jwtService.getUserIdFromJwtLong(jwt);
      Optional<DbUser> optionalDbUser = dbUserservice.getUserById(userId);
      if (!optionalDbUser.isPresent()) {
        return ResponseEntity
          .badRequest()
          .body(dbUserResponse.getMeNoUserFoundWithThisJwtTokenId());
      }
      DbUser dbUser = optionalDbUser.get();

      Comment comment = new Comment();
      comment
        .setContent(commentRequest.getContent())
        .setDbUser(dbUser)
        .setPost(post)
        .setCreatedAt(LocalDateTime.now());

      Comment savedComment = commentService.saveComment(comment);

      List<Comment> commentList = post.getComments();

      return ResponseEntity
        .ok()
        .body(
          entityAndDtoCreation.getPostWithCommentsDtoFromPostWithCommentEntity(
            post
          )
        );
    } catch (Exception e) {
      return ResponseEntity.internalServerError().build();
    }
  }
}
