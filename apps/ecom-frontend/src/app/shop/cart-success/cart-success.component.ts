import { afterNextRender, Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { injectQueryParams } from 'ngxtension/inject-query-params';
import { CartService } from '../cart.service';
import { FaIconComponent } from '@fortawesome/angular-fontawesome';

@Component({
  selector: 'ecom-cart-success',
  standalone: true,
  imports: [CommonModule, FaIconComponent],
  templateUrl: './cart-success.component.html',
  styleUrl: './cart-success.component.scss',
})
export class CartSuccessComponent {
  sessionId = injectQueryParams('session_id');
  cartService = inject(CartService);

  isValidAccess = true;

  constructor() {
    afterNextRender(() => this.verifySession());
  }

  verifySession() {
    const sessionIdLocalStorage = this.cartService.getSessionId();
    if (sessionIdLocalStorage !== this.sessionId()) {
      this.isValidAccess = false;
    } else {
      this.cartService.deleteSessionId();
      this.cartService.clearCart();
    }
  }
}
