import { Component, effect, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { AdminProductService } from '../../admin-product.service';
import { ToastService } from '../../../shared/toast/toast.service';
import {
  injectMutation,
  injectQuery,
  injectQueryClient,
} from '@tanstack/angular-query-experimental';
import { lastValueFrom } from 'rxjs';
import { FaIconComponent } from '@fortawesome/angular-fontawesome';

@Component({
  selector: 'ecom-admin-categories',
  standalone: true,
  imports: [CommonModule, RouterLink, FaIconComponent],
  templateUrl: './admin-categories.component.html',
  styleUrl: './admin-categories.component.scss',
})
export class AdminCategoriesComponent {
  productAdminService = inject(AdminProductService);

  toastService = inject(ToastService);
  queryClient = injectQueryClient();

  constructor() {
    effect(() => this.handleCategoriesQueryError());
  }

  categoriesQuery = injectQuery(() => ({
    queryKey: ['categories'],
    queryFn: () => lastValueFrom(this.productAdminService.findAllCategories()),
  }));

  deleteMutation = injectMutation(() => ({
    mutationFn: (categoryPublicId: string) =>
      lastValueFrom(this.productAdminService.deleteCategory(categoryPublicId)),
    onSuccess: () => this.onDeletionSuccess(),
    onError: () => this.onDeletionError(),
  }));

  private onDeletionSuccess(): void {
    this.queryClient.invalidateQueries({ queryKey: ['categories'] });
    this.toastService.show('Category deleted', 'SUCCESS');
  }

  private onDeletionError(): void {
    this.toastService.show('Issue when deleting category', 'ERROR');
  }

  private handleCategoriesQueryError() {
    if (this.categoriesQuery.isError()) {
      this.toastService.show(
        'Error! Failed to load categories. please try again.',
        'ERROR'
      );
    }
  }

  deleteCategory(publicId: string) {
    this.deleteMutation.mutate(publicId);
  }
}
