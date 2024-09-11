import {
  ActivatedRouteSnapshot,
  CanActivateFn,
  RouterStateSnapshot,
} from '@angular/router';
import { inject, PLATFORM_ID } from '@angular/core';
import { Oauth2Service } from './oauth2.service';
import { isPlatformBrowser } from '@angular/common';
import { filter, interval, map, timeout } from 'rxjs';

export const roleCheckGuard: CanActivateFn = (
  next: ActivatedRouteSnapshot,
  state: RouterStateSnapshot
) => {
  const oauth2Service = inject(Oauth2Service);
  const platformId = inject(PLATFORM_ID);

  if (isPlatformBrowser(platformId)) {
    const authorities = next.data['authorities'];
    return interval(50).pipe(
      filter(() => oauth2Service.connectedUserQuery?.isPending() == false),
      map(() => oauth2Service.connectedUserQuery?.data()),
      map(
        (data) =>
          !authorities ||
          authorities.length === 0 ||
          oauth2Service.hasAnyAuthorities(data!, authorities)
      ),
      timeout(3000)
    );
  } else {
    return false;
  }
};
