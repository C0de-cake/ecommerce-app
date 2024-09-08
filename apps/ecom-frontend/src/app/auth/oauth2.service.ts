import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { OidcSecurityService } from 'angular-auth-oidc-client';
import {
  CreateQueryResult,
  injectQuery,
} from '@tanstack/angular-query-experimental';
import { ConnectedUser } from '../shared/model/user.model';
import { firstValueFrom, Observable } from 'rxjs';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root',
})
export class Oauth2Service {
  http = inject(HttpClient);

  oidcSecurityService = inject(OidcSecurityService);

  connectedUserQuery: CreateQueryResult<ConnectedUser> | undefined;

  notConnected = 'NOT_CONNECTED';

  fetch(): CreateQueryResult<ConnectedUser> {
    return injectQuery(() => ({
      queryKey: ['connected-user'],
      queryFn: () => firstValueFrom(this.fetchUserHttp(false)),
    }));
  }

  fetchUserHttp(forceResync: boolean): Observable<ConnectedUser> {
    const params = new HttpParams().set('forceResync', forceResync);
    return this.http.get<ConnectedUser>(
      `${environment.apiUrl}/users/authenticated`,
      { params }
    );
  }

  login(): void {
    this.oidcSecurityService.authorize();
  }

  logout(): void {
    this.oidcSecurityService.logoff().subscribe();
  }

  initAuthentication(): void {
    this.oidcSecurityService.checkAuth().subscribe((authInfo) => {
      if (authInfo.isAuthenticated) {
        console.log('connected');
      } else {
        console.log('not connected');
      }
    });
  }

  hasAnyAuthorities(
    connectedUser: ConnectedUser,
    authorities: Array<string> | string
  ): boolean {
    if (!Array.isArray(authorities)) {
      authorities = [authorities];
    }
    if (connectedUser.authorities) {
      return connectedUser.authorities.some((authority: string) =>
        authorities.includes(authority)
      );
    } else {
      return false;
    }
  }
}
