import { Directive, Input, ElementRef, OnInit, OnDestroy } from '@angular/core';
import { AbstractControl } from '@angular/forms';
import { Subscription } from 'rxjs';
import { startWith } from 'rxjs/operators';
import { MatError } from '@angular/material/form-field';

const defaultMessages: { [k: string]: string } = {
  required: 'Ce champ est requis',
  minlength: 'Trop court',
  email: 'Email invalide'
}

@Directive({
  selector: '[appInsertError]',
  standalone: true
})
export class InsertErrorDirective implements OnInit, OnDestroy {
  @Input('appInsertError') control?: AbstractControl;
  private subscription?: Subscription;

  constructor(
    private elementRef: ElementRef
  ) {}

  ngOnInit(): void {
    this.subscription = this.control?.statusChanges
      .pipe(startWith(this.control?.status))
      .subscribe(() => {
        const element = this.elementRef.nativeElement;
        if (this.control?.errors && element) {
          const errorKey = Object.keys(this.control.errors)[0];
          const message = this.control.errors[errorKey]?.message ?? defaultMessages[errorKey] ?? errorKey;
          element.innerHTML = message;
        } else if (element) {
          element.innerHTML = '';
        }
      });
  }

  ngOnDestroy(): void {
    this.subscription?.unsubscribe();
  }
} 