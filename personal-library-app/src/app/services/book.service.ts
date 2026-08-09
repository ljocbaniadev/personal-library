import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { environment } from '../../environments/environment';
import { Observable } from 'rxjs';
import { ApiResponse } from '../models/api-response';
import { Book } from '../models/book/book';
import { BookRequest } from '../models/book/book-request';

@Injectable({
  providedIn: 'root',
})
export class BookService {
  private http = inject(HttpClient);
  private api = environment.apiUrl + '/books';

  getBooks(): Observable<ApiResponse<Book[]>> {
    return this.http.get<ApiResponse<Book[]>>(this.api);
  }

  getBook(id: number): Observable<ApiResponse<Book>> {
    return this.http.get<ApiResponse<Book>>(`${this.api}/${id}`);
  }

  createBook(request: BookRequest) {
    return this.http.post<ApiResponse<Book>>(this.api, request);
  }

  updateBook(id: number, request: BookRequest) {
    return this.http.put<ApiResponse<Book>>(`${this.api}/${id}`, request);
  }

  deleteBook(id: number) {
    return this.http.delete<ApiResponse<any>>(`${this.api}/${id}`);
  }
}
