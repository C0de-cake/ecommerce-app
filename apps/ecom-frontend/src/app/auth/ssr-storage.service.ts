import { inject, Injectable, PLATFORM_ID } from '@angular/core';
import { AbstractSecurityStorage } from 'angular-auth-oidc-client';
import { isPlatformBrowser } from '@angular/common';

@Injectable({
  providedIn: 'root',
})
export class SsrStorageService implements AbstractSecurityStorage {
  private platformId = inject(PLATFORM_ID);

  clear(): void {
    if (isPlatformBrowser(this.platformId)) {
      sessionStorage.clear();
    }
  }

  read(key: string): string | null {
    if (isPlatformBrowser(this.platformId)) {
      return sessionStorage.getItem(key);
    } else {
      return null;
    }
  }

  remove(key: string): void {
    if (isPlatformBrowser(this.platformId)) {
      sessionStorage.removeItem(key);
    }
  }

  write(key: string, value: string): void {
    if (isPlatformBrowser(this.platformId)) {
      sessionStorage.setItem(key, value);
    }
  }
}
