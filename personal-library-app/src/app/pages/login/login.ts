import { Component, inject, signal } from '@angular/core';
import { RouterLink } from '@angular/router';

import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { Router } from '@angular/router';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { AuthService } from '../../services/auth.service';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { NotificationService } from '../../services/notification.service';

@Component({
  selector: 'app-login',
  imports: [
    RouterLink,
    MatCardModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    ReactiveFormsModule,
    MatProgressSpinnerModule,
  ],
  templateUrl: './login.html',
  styleUrl: './login.css',
})
export class Login {
  private fb = inject(FormBuilder);
  private authService = inject(AuthService);
  private router = inject(Router);
  private notification = inject(NotificationService);

  loading = signal(false);

  loginForm = this.fb.group({
    username: ['', Validators.required],
    password: ['', Validators.required],
  });

  login(): void {
    if (this.loginForm.invalid) {
      this.loginForm.markAllAsTouched();

      this.notification.warning('Please enter your username and password.');

      return;
    }

    this.loading.set(true);

    this.authService.login(this.loginForm.value as any).subscribe({
      next: (response) => {
        this.loading.set(false);

        this.authService.saveToken(response.data.token);

        this.notification.success('Welcome back, Archivist.');

        this.router.navigate(['/dashboard']);
      },

      error: (error) => {
        this.loading.set(false);

        if (error.status === 429) {
          this.notification.error('Too many login attempts. Please wait before trying again.');
          return;
        }

        if (error.status === 401 || error.status === 403) {
          this.notification.error('Username or password is invalid.');
          return;
        }

        this.notification.error(
          error.error?.message ?? 'Unable to reach the Archive. Please try again later.',
        );
      },
    });
  }
}
