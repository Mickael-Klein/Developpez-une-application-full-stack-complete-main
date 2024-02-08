import { Component, Input } from '@angular/core';
import { formInput } from '../../interface/formInput.interface';
import { ReactiveFormsModule } from '@angular/forms';

@Component({
  selector: 'app-form-input',
  standalone: true,
  imports: [ReactiveFormsModule],
  templateUrl: './form-input.component.html',
  styleUrl: './form-input.component.scss',
})
export class FormInputComponent {
  hasError = false;
  @Input() formInputData!: formInput;
}
