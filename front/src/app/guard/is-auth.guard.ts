import { inject } from '@angular/core';
import {
  ActivatedRouteSnapshot,
  CanActivateFn,
  Router,
  RouterStateSnapshot,
} from '@angular/router';
import { SessionService } from '../core/service/session/session.service';

export const isAuthGuard: CanActivateFn = (
  route: ActivatedRouteSnapshot,
  state: RouterStateSnapshot
) => {
  const sessionService = inject(SessionService);
  const router = inject(Router);

  if (sessionService.isLogged) {
    return true;
  } else {
    router.navigateByUrl('/home');
    return false;
  }
};
