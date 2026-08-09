import { Book } from './book/book';

export interface DashboardResponse {
  totalBooks: number;
  inProgressBooks: number;
  completedBooks: number;
  favoriteBooks: number;
  recentlyAddedBooks: Book[];
}
