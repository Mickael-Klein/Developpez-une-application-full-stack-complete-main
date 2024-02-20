import { inject } from '@angular/core';
import {
  ActivatedRouteSnapshot,
  CanActivateFn,
  Router,
  RouterStateSnapshot,
} from '@angular/router';
import { SessionService } from '../core/service/session/session.service';
import { Observable, filter, map } from 'rxjs';

export const isAuthGuard: CanActivateFn = (
  route: ActivatedRouteSnapshot,
  state: RouterStateSnapshot
): boolean | Observable<boolean> => {
  const sessionService = inject(SessionService);
  const router = inject(Router);

  const checkLoginState = (): boolean => {
    if (sessionService.isLogged) {
      return true;
    } else {
      router.navigateByUrl('/home');
      return false;
    }
  };

  if (!sessionService.isLoading) {
    return checkLoginState();
  } else {
    return sessionService.$isLoading().pipe(
      filter((isLoading: boolean) => !isLoading),
      map(() => {
        return checkLoginState();
      })
    );
  }
};
