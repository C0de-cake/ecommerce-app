import { Component, effect, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AdminProductService } from '../../admin-product.service';
import { ToastService } from '../../../shared/toast/toast.service';
import {
  injectMutation,
  injectQuery,
  injectQueryClient,
} from '@tanstack/angular-query-experimental';
import { Pagination } from '../../../shared/model/request.model';
import { lastValueFrom } from 'rxjs';
import { RouterLink } from '@angular/router';
import { FaIconComponent } from '@fortawesome/angular-fontawesome';

@Component({
  selector: 'ecom-admin-products',
  standalone: true,
  imports: [CommonModule, RouterLink, FaIconComponent],
  templateUrl: './admin-products.component.html',
  styleUrl: './admin-products.component.scss',
})
export class AdminProductsComponent {
  productService = inject(AdminProductService);
  toastService = inject(ToastService);
  queryClient = injectQueryClient();

  pageRequest: Pagination = {
    page: 0,
    size: 20,
    sort: ['createdDate,desc'],
  };

  constructor() {
    effect(() => this.handleProductQueryError());
  }

  productsQuery = injectQuery(() => ({
    queryKey: ['products', this.pageRequest],
    queryFn: () =>
      lastValueFrom(this.productService.findAllProducts(this.pageRequest)),
  }));

  deleteMutation = injectMutation(() => ({
    mutationFn: (productPublicId: string) =>
      lastValueFrom(this.productService.deleteProduct(productPublicId)),
    onSuccess: () => this.onDeletionSuccess(),
    onError: () => this.onDeletionError(),
  }));

  deleteProduct(publicId: string) {
    this.deleteMutation.mutate(publicId);
  }

  private onDeletionSuccess() {
    this.queryClient.invalidateQueries({ queryKey: ['products'] });
    this.toastService.show('Product deleted', 'SUCCESS');
  }

  private onDeletionError() {
    this.toastService.show('Issue when deleting product', 'ERROR');
  }

  private handleProductQueryError() {
    if (this.productsQuery.isError()) {
      this.toastService.show(
        'Error failed to load products, please try again',
        'ERROR'
      );
    }
  }
}
