import { Component, inject } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { AuthService } from '../../services/auth.service';

import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { NotificationService } from '../../services/notification.service';

@Component({
  selector: 'app-register',
  imports: [
    RouterLink,
    MatCardModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    ReactiveFormsModule,
  ],
  templateUrl: './register.html',
  styleUrl: './register.css',
})
export class Register {
  private fb = inject(FormBuilder);
  private authService = inject(AuthService);
  private router = inject(Router);
  private notification = inject(NotificationService);

  registerForm = this.fb.group({
    username: ['', Validators.required, Validators.minLength(3)],
    email: ['', [Validators.required, Validators.email]],
    password: ['', [Validators.required, Validators.minLength(6)]],
    confirmPassword: ['', Validators.required],
  });

  register(): void {
    if (this.registerForm.invalid) {
      this.registerForm.markAllAsTouched();
      this.notification.warning('Please complete the form.');
      return;
    }

    const form = this.registerForm.getRawValue();

    if (form.password !== form.confirmPassword) {
      this.notification.warning('Passwords do not match.');
      return;
    }

    this.authService
      .register({
        username: form.username!,
        email: form.email!,
        password: form.password!,
      })
      .subscribe({
        next: () => {
          alert('Registration successful.');
          this.router.navigate(['/login']);
        },

        error: (error) => {
          console.error(error);
          if (error.status === 429) {
            this.notification.error(
              'Too many registration attempts. Please wait before trying again.',
            );
            return;
          }

          this.notification.error(
            error.error?.message ?? 'Unable to reach the Archive. Please try again later.',
          );
        },
      });
  }
}
