import { Component, Input, OnInit } from '@angular/core';
import { Post } from '../../core/model/Post.model';
import { Router } from '@angular/router';
import { DatePipe } from '@angular/common';

@Component({
  selector: 'app-post-card',
  standalone: true,
  imports: [DatePipe],
  templateUrl: './post-card.component.html',
  styleUrl: './post-card.component.scss',
})
export class PostCardComponent {
  @Input() postProps!: Post;

  constructor(private router: Router) {}

  onPostClick() {
    this.router.navigateByUrl(`/article/${this.postProps.id}`);
  }
}
