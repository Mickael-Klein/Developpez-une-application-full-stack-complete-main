package com.openclassrooms.mdd.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * Model class representing a post entity.
 */
@Data
@Accessors(chain = true)
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "post")
public class Post {

  /** The unique identifier of the post. */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  /** The title of the post. */
  @NotNull
  @Size(max = 60)
  @Column(name = "title")
  private String title;

  /** The content of the post. */
  @NotNull
  @Size(min = 10)
  @Column(name = "content")
  private String content;

  /** The user who authored the post. */
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "author_id")
  private DbUser dbUser;

  /** The subject to which the post belongs. */
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "subject_id")
  private Subject subject;

  /** The date and time when the post was created. */
  @NotNull
  @Column(name = "created_at")
  private LocalDateTime createdAt;

  /** The list of comments associated with the post. */
  @OneToMany(
    mappedBy = "post",
    cascade = CascadeType.ALL,
    orphanRemoval = true,
    fetch = FetchType.LAZY
  )
  private List<Comment> comments;
}
