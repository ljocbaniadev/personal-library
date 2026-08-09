import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DeleteBookDialog } from './delete-book-dialog';

describe('DeleteBookDialog', () => {
  let component: DeleteBookDialog;
  let fixture: ComponentFixture<DeleteBookDialog>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DeleteBookDialog],
    }).compileComponents();

    fixture = TestBed.createComponent(DeleteBookDialog);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
