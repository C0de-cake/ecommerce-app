import { TestBed } from '@angular/core/testing';

import { SsrStorageService } from './ssr-storage.service';

describe('SsrStorageService', () => {
  let service: SsrStorageService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SsrStorageService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
