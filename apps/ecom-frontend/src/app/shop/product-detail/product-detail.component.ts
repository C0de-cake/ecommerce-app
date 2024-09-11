import { Component, effect, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { injectParams } from 'ngxtension/inject-params';
import { UserProductService } from '../../shared/service/user-product.service';
import { Router } from '@angular/router';
import { ToastService } from '../../shared/toast/toast.service';
import { Pagination } from '../../shared/model/request.model';
import { lastValueFrom } from 'rxjs';
import { injectQuery } from '@tanstack/angular-query-experimental';
import { FaIconComponent } from '@fortawesome/angular-fontawesome';
import { ProductCardComponent } from '../product-card/product-card.component';

@Component({
  selector: 'ecom-product-detail',
  standalone: true,
  imports: [CommonModule, FaIconComponent, ProductCardComponent],
  templateUrl: './product-detail.component.html',
  styleUrl: './product-detail.component.scss',
})
export class ProductDetailComponent {
  publicId = injectParams('publicId');

  productService = inject(UserProductService);
  router = inject(Router);
  toastService = inject(ToastService);

  lastPublicId = '';

  pageRequest: Pagination = {
    page: 0,
    size: 20,
    sort: [],
  };

  constructor() {
    effect(() => this.handlePublicIdChange());
    effect(() => this.handleProductQueryError());
    effect(() => this.handleRelatedProductQueryError());
  }

  productQuery = injectQuery(() => ({
    queryKey: ['product', this.publicId()],
    queryFn: () =>
      lastValueFrom(this.productService.findOneByPublicId(this.publicId()!)),
  }));

  relatedProductQuery = injectQuery(() => ({
    queryKey: ['related-product', this.publicId(), this.pageRequest],
    queryFn: () =>
      lastValueFrom(
        this.productService.findRelatedProduct(
          this.pageRequest,
          this.publicId()!
        )
      ),
  }));

  private handlePublicIdChange() {
    if (this.publicId()) {
      if (this.lastPublicId != this.publicId() && this.lastPublicId !== '') {
        this.relatedProductQuery.refetch();
        this.productQuery.refetch();
      }
      this.lastPublicId = this.publicId()!;
    }
  }

  private handleRelatedProductQueryError() {
    if (this.relatedProductQuery.isError()) {
      this.toastService.show(
        'Error! Failed to load related product. please try again.',
        'ERROR'
      );
    }
  }

  private handleProductQueryError() {
    if (this.productQuery.isError()) {
      this.toastService.show(
        'Error! Failed to load product. please try again.',
        'ERROR'
      );
    }
  }
}
