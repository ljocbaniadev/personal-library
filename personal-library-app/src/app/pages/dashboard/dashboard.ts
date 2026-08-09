import { Component } from '@angular/core';
import { DashboardService } from '../../services/dashboard.service';
import { inject, OnInit } from '@angular/core';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { Book } from '../../models/book/book';

@Component({
  selector: 'app-dashboard',
  imports: [MatIconModule, MatButtonModule],
  templateUrl: './dashboard.html',
  styleUrl: './dashboard.css',
})
export class Dashboard implements OnInit {
  private dashboardService = inject(DashboardService);
  totalBooks = 0;
  inProgressBooks = 0;
  completedBooks = 0;
  favoriteBooks = 0;

  recentlyAddedBooks: Book[] = [];

  tarotCards = [
    {
      name: 'The Fool',
      character: 'Klein Moretti',
      pathway: 'Fool Pathway',
      symbol: '✦',
      message:
        'The gray fog stirs. A new journey begins, and the pathway ahead is yours to discover.',
    },
    {
      name: 'The Hanged Man',
      character: 'Alger Wilson',
      pathway: 'Tyrant Pathway',
      symbol: '⛓',
      message: 'The sea hides more than it reveals. Sacrifice and patience may reveal the truth.',
    },
    {
      name: 'Justice',
      character: 'Audrey Hall',
      pathway: 'Spectator Pathway',
      symbol: '⚖',
      message: 'Observe. Understand. Judge. The mind sees what others fail to notice.',
    },
    {
      name: 'The Sun',
      character: 'Derrick Berg',
      pathway: 'Sun Pathway',
      symbol: '☀',
      message: 'Light shall prevail. Even in the darkest depths, hope continues to shine.',
    },
    {
      name: 'The Magician',
      character: 'Fors Wall',
      pathway: 'Apprentice Pathway',
      symbol: '☿',
      message: 'The world is a story waiting to be written. Every possibility has a path.',
    },
    {
      name: 'The Hermit',
      character: 'Cattleya',
      pathway: 'Hermit Pathway',
      symbol: '♧',
      message: 'Knowledge is hidden in the unknown. Seek the mysteries beyond the ordinary.',
    },
    {
      name: 'The Moon',
      character: 'Emlyn White',
      pathway: 'Moon Pathway',
      symbol: '☽',
      message: 'Not everything is as it appears beneath the veil of mystery.',
    },
    {
      name: 'The World',
      character: 'Gehrman Sparrow',
      pathway: 'Fool Pathway',
      symbol: '🌐',
      message: 'A new identity. A new journey. The world awaits the arrival of the adventurer.',
    },
    {
      name: 'The Star',
      character: 'Leonard Mitchell',
      pathway: 'Darkness Pathway',
      symbol: '★',
      message:
        'A distant star watches from the darkness. Some secrets are closer than they appear.',
    },
    {
      name: 'Judgement',
      character: 'Xio Derecha',
      pathway: 'Arbiter Pathway',
      symbol: '⚔',
      message: 'The time for judgement will come. Every action carries its own consequence.',
    },
  ];

  drawnCard: any = null;

  ngOnInit(): void {
    this.loadDashboard();
  }

  loadDashboard(): void {
    this.dashboardService.getDashboard().subscribe({
      next: (response) => {
        this.totalBooks = response.data.totalBooks;
        this.inProgressBooks = response.data.inProgressBooks;
        this.completedBooks = response.data.completedBooks;
        this.favoriteBooks = response.data.favoriteBooks;
        this.recentlyAddedBooks = response.data.recentlyAddedBooks;
      },

      error: (error) => {
        console.error(error);
      },
    });
  }

  drawTarotCard(): void {
    const randomIndex = Math.floor(Math.random() * this.tarotCards.length);

    this.drawnCard = this.tarotCards[randomIndex];
  }
}
