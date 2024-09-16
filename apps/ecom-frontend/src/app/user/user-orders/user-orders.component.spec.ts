import { ComponentFixture, TestBed } from '@angular/core/testing';
import { UserOrdersComponent } from './user-orders.component';

describe('UserOrdersComponent', () => {
  let component: UserOrdersComponent;
  let fixture: ComponentFixture<UserOrdersComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [UserOrdersComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(UserOrdersComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
