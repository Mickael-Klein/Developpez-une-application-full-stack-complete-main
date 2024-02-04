package com.openclassrooms.mdd.controller;

import com.openclassrooms.mdd.model.DbUser;
import com.openclassrooms.mdd.model.Post;
import com.openclassrooms.mdd.model.Subject;
import com.openclassrooms.mdd.service.JwtServiceImpl;
import com.openclassrooms.mdd.serviceInterface.DbUserInterface;
import com.openclassrooms.mdd.serviceInterface.PostInterface;
import com.openclassrooms.mdd.serviceInterface.SubjectInterface;
import com.openclassrooms.mdd.util.entityAndDtoCreation.EntityAndDtoCreation;
import com.openclassrooms.mdd.util.payload.request.PostRequest;
import com.openclassrooms.mdd.util.payload.response.DbUserResponse;
import com.openclassrooms.mdd.util.payload.response.PostResponse;
import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/post")
public class PostController {

  @Autowired
  private PostInterface postService;

  @Autowired
  private EntityAndDtoCreation entityAndDtoCreation;

  @Autowired
  private JwtServiceImpl jwtService;

  @Autowired
  private DbUserInterface dbUserService;

  @Autowired
  private SubjectInterface subjectService;

  @Autowired
  private PostResponse postResponse;

  @Autowired
  private DbUserResponse dbUserResponse;

  @PostMapping("/create")
  public ResponseEntity<?> createPost(
    @AuthenticationPrincipal Jwt jwt,
    @Valid @RequestBody PostRequest postRequest,
    BindingResult bindingResult
  ) {
    try {
      boolean isRequestPayloadInvalid = bindingResult.hasErrors();
      if (isRequestPayloadInvalid) {
        return ResponseEntity
          .badRequest()
          .body(postResponse.postPostInvalidPayload());
      }

      long userId = jwtService.getUserIdFromJwtLong(jwt);
      Optional<DbUser> optionalDbUser = dbUserService.getUserById(userId);
      if (!optionalDbUser.isPresent()) {
        return ResponseEntity
          .badRequest()
          .body(dbUserResponse.getMeNoUserFoundWithThisJwtTokenId());
      }

      DbUser dbUser = optionalDbUser.get();

      Optional<Subject> optionalSubject = subjectService.getSubjectById(
        postRequest.getSubjectId()
      );
      if (!optionalSubject.isPresent()) {
        return ResponseEntity
          .badRequest()
          .body(postResponse.postPostInvalidSubjectId());
      }

      Subject subject = optionalSubject.get();

      Post post = new Post();
      post
        .setTitle(postRequest.getTitle())
        .setContent(postRequest.getContent())
        .setDbUser(dbUser)
        .setSubject(subject)
        .setCreatedAt(LocalDateTime.now());

      Post savedPost = postService.savePost(post);

      return ResponseEntity
        .ok()
        .body(entityAndDtoCreation.getPostDtoFromPostEntity(savedPost));
    } catch (Exception e) {
      return ResponseEntity.internalServerError().build();
    }
  }

  @GetMapping("/getpost/{postId}")
  public ResponseEntity<?> getPostById(@PathVariable final long postId) {
    try {
      Optional<Post> optionalPost = postService.getPostWithCommentsById(postId);
      if (!optionalPost.isPresent()) {
        return ResponseEntity
          .badRequest()
          .body(postResponse.getPostInvalidPostId());
      }

      Post post = optionalPost.get();

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
