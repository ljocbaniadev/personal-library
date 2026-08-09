import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';

import { MatDialogModule, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatButtonModule } from '@angular/material/button';

@Component({
  selector: 'app-add-book-dialog',
  imports: [
    ReactiveFormsModule,
    MatDialogModule,
    MatFormFieldModule,
    MatInputModule,
    MatSelectModule,
    MatButtonModule,
  ],
  templateUrl: './add-book-dialog.html',
  styleUrl: './add-book-dialog.css',
})
export class AddBookDialog {
  bookForm!: FormGroup;

  constructor(
    private formBuilder: FormBuilder,
    private dialogRef: MatDialogRef<AddBookDialog>,
    @Inject(MAT_DIALOG_DATA)
    public data: any,
  ) {}

  ngOnInit(): void {
    this.bookForm = this.formBuilder.group({
      title: ['', Validators.required],
      country: [''],
      genre: [''],
      protagonist: [''],
      timePeriod: [''],
      status: ['', Validators.required],
      rating: [''],
      bookType: [''],
      tags: [''],
      chapter: [''],
      comment: [''],
    });

    if (this.data?.book) {
      this.bookForm.patchValue(this.data.book);
    }
  }

  saveBook(): void {
    if (this.bookForm.invalid) {
      this.bookForm.markAllAsTouched();
      return;
    }

    this.dialogRef.close(this.bookForm.value);
  }

  cancel(): void {
    this.dialogRef.close();
  }
}
