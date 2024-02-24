package com.openclassrooms.mdd.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Size;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * Model class representing a subject entity.
 */
@Data
@Builder
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(
  name = "subject",
  uniqueConstraints = { @UniqueConstraint(columnNames = "name") }
)
public class Subject {

  /** The unique identifier of the subject. */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  /** The name of the subject. */
  @NonNull
  @Size(max = 60)
  @Column(name = "name")
  private String name;

  /** The description of the subject. */
  @NonNull
  @Size(min = 30)
  @Column(name = "description")
  private String description;

  /** The list of posts associated with the subject. */
  @OneToMany(
    mappedBy = "subject",
    cascade = CascadeType.ALL,
    orphanRemoval = true,
    fetch = FetchType.LAZY
  )
  private List<Post> posts;

  /** The list of users associated with the subject. */
  @ManyToMany(mappedBy = "subjects")
  private List<DbUser> users;
}
