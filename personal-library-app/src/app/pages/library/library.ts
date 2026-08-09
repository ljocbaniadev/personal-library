import { AfterViewInit, Component, inject, ViewChild } from '@angular/core';

import { MatButtonModule } from '@angular/material/button';
import { MatTableDataSource, MatTableModule } from '@angular/material/table';
import { MatSort, MatSortModule } from '@angular/material/sort';
import { MatPaginator, MatPaginatorModule } from '@angular/material/paginator';
import { MatIconModule } from '@angular/material/icon';
import { MatDialog, MatDialogModule } from '@angular/material/dialog';
import { AddBookDialog } from './add-book-dialog/add-book-dialog';
import { Book } from '../../models/book/book';
import { BookService } from '../../services/book.service';
import { BookRequest } from '../../models/book/book-request';
import { DeleteBookDialog } from './delete-book-dialog/delete-book-dialog';
import { FormsModule } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { NotificationService } from '../../services/notification.service';

@Component({
  selector: 'app-library',
  imports: [
    MatButtonModule,
    MatTableModule,
    MatSortModule,
    MatPaginatorModule,
    MatIconModule,
    MatDialogModule,
    FormsModule,
    MatFormFieldModule,
    MatInputModule,
    MatSelectModule,
    MatSnackBarModule,
  ],
  templateUrl: './library.html',
  styleUrl: './library.css',
})
export class Library implements AfterViewInit {
  private bookService = inject(BookService);
  private notification = inject(NotificationService);

  displayedColumns: string[] = [
    'title',
    'country',
    'genre',
    'protagonist',
    'timePeriod',
    'status',
    'rating',
    'bookType',
    'tags',
    'chapter',
    'comment',
    'actions',
  ];

  books: Book[] = [];
  searchText = '';
  selectedCountry = 'All';
  selectedPeriod = 'All';
  selectedStatus = 'All';
  countries: string[] = [];
  periods: string[] = [];
  statuses: string[] = [];

  dataSource = new MatTableDataSource(this.books);

  @ViewChild(MatSort) sort!: MatSort;
  @ViewChild(MatPaginator) paginator!: MatPaginator;

  constructor(private dialog: MatDialog) {}

  ngOnInit(): void {
    this.loadBooks();
  }

  ngAfterViewInit(): void {
    this.dataSource.sort = this.sort;
    this.dataSource.paginator = this.paginator;

    this.paginator.pageSize = 10;
  }

  loadBooks(): void {
    this.bookService.getBooks().subscribe({
      next: (response) => {
        this.books = response.data;
        this.dataSource.data = [...this.books];
        this.countries = ['All', ...new Set(this.books.map((x) => x.country).filter(Boolean))];
        this.periods = ['All', ...new Set(this.books.map((x) => x.timePeriod).filter(Boolean))];
        this.statuses = ['All', ...new Set(this.books.map((x) => x.status).filter(Boolean))];
        this.initializeFilter();
        this.dataSource.sort = this.sort;
      },
    });
  }

  openAddBookDialog(): void {
    const dialogRef = this.dialog.open(AddBookDialog, {
      width: '600px',
      maxWidth: '95vw',
      maxHeight: '90vh',
      panelClass: 'library-dialog',
    });

    dialogRef.afterClosed().subscribe((request: BookRequest | undefined) => {
      if (!request) {
        return;
      }

      this.bookService.createBook(request).subscribe({
        next: () => {
          this.loadBooks();
          this.notification.success('📚 A new tome has been archived.');
        },
        error: () => {
          this.notification.error('Failed to archive the tome.');
        },
      });
    });
  }

  openEditBookDialog(book: Book): void {
    const dialogRef = this.dialog.open(AddBookDialog, {
      width: '600px',
      maxWidth: '95vw',
      maxHeight: '90vh',
      panelClass: 'library-dialog',

      data: {
        book: book,
      },
    });

    dialogRef.afterClosed().subscribe((updatedBook: BookRequest | undefined) => {
      if (!updatedBook) {
        return;
      }

      this.bookService.updateBook(book.id, updatedBook).subscribe({
        next: () => {
          this.loadBooks();
          this.notification.success('✒️ The tome has been revised.');
        },
        error: (error) => {
          console.error(error);
          this.notification.error('Failed to revise the tome.');
        },
      });
    });
  }

  openDeleteBookDialog(book: Book): void {
    const dialogRef = this.dialog.open(DeleteBookDialog, {
      width: '420px',
      panelClass: 'library-dialog',
      data: {
        title: book.title,
      },
    });

    dialogRef.afterClosed().subscribe((confirmed: boolean) => {
      if (!confirmed) {
        return;
      }

      this.bookService.deleteBook(book.id).subscribe({
        next: () => {
          this.loadBooks();
          this.notification.success('🗑️ The tome has been removed from the Archive.');
        },
        error: (error) => {
          console.error(error);
          this.notification.error('Failed to remove the tome.');
        },
      });
    });
  }

  initializeFilter(): void {
    this.dataSource.filterPredicate = (book: Book, filter: string) => {
      const filters = JSON.parse(filter);
      const search = filters.search.toLowerCase();

      const matchesSearch =
        book.title?.toLowerCase().includes(search) ||
        book.country?.toLowerCase().includes(search) ||
        book.genre?.toLowerCase().includes(search) ||
        book.protagonist?.toLowerCase().includes(search) ||
        book.timePeriod?.toLowerCase().includes(search) ||
        book.status?.toLowerCase().includes(search) ||
        book.bookType?.toLowerCase().includes(search) ||
        book.tags?.toLowerCase().includes(search) ||
        book.comment?.toLowerCase().includes(search);
      const matchesCountry = filters.country === 'All' || book.country === filters.country;
      const matchesPeriod = filters.period === 'All' || book.timePeriod === filters.period;
      const matchesStatus = filters.status === 'All' || book.status === filters.status;

      return matchesSearch && matchesCountry && matchesPeriod && matchesStatus;
    };

    this.applyFilter();
  }

  applyFilter(): void {
    this.dataSource.filter = JSON.stringify({
      search: this.searchText,
      country: this.selectedCountry,
      period: this.selectedPeriod,
      status: this.selectedStatus,
    });

    if (this.dataSource.paginator) {
      this.dataSource.paginator.firstPage();
    }
  }

  hasActiveFilters(): boolean {
    return (
      this.searchText.trim() !== '' ||
      this.selectedCountry !== 'All' ||
      this.selectedPeriod !== 'All' ||
      this.selectedStatus !== 'All'
    );
  }

  clearFilters(): void {
    this.searchText = '';

    this.selectedCountry = 'All';
    this.selectedPeriod = 'All';
    this.selectedStatus = 'All';

    this.applyFilter();
  }
}
