import { Post } from './Post.model';

export class Subject {
  constructor(
    public id: number,
    public name: string,
    public description: string,
    public postIds?: number[]
  ) {}
}
