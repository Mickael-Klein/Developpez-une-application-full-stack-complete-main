import {
  ActivatedRouteSnapshot,
  CanActivateFn,
  Router,
  RouterStateSnapshot,
} from '@angular/router';
import { SessionService } from '../core/service/session/session.service';
import { inject } from '@angular/core';
import { Observable, filter, map } from 'rxjs';

export const isNotAuthGuard: CanActivateFn = (
  route: ActivatedRouteSnapshot,
  state: RouterStateSnapshot
): boolean | Observable<boolean> => {
  const sessionService = inject(SessionService);
  const router = inject(Router);

  const checkLoginState = (): boolean => {
    if (!sessionService.isLogged) {
      return true;
    } else {
      router.navigateByUrl('/articles');
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
