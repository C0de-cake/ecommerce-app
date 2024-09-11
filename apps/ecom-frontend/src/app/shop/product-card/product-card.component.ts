import { Component, input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Product } from '../../admin/model/product.model';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'ecom-product-card',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './product-card.component.html',
  styleUrl: './product-card.component.scss',
})
export class ProductCardComponent {
  product = input.required<Product>();
}
