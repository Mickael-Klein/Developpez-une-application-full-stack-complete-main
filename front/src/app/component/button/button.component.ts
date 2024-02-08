import { Component, Input } from '@angular/core';
import { Button } from '../../interface/Button.interface';

@Component({
  selector: 'app-button',
  standalone: true,
  imports: [],
  templateUrl: './button.component.html',
  styleUrl: './button.component.scss',
})
export class ButtonComponent {
  @Input() buttonProps!: Button;
}
