import {
  ActivatedRouteSnapshot,
  CanActivateFn,
  Router,
  RouterStateSnapshot,
} from '@angular/router';
import { SessionService } from '../core/service/session/session.service';
import { inject } from '@angular/core';

export const isNotAuthGuard: CanActivateFn = (
  route: ActivatedRouteSnapshot,
  state: RouterStateSnapshot
) => {
  const sessionService = inject(SessionService);
  const router = inject(Router);

  if (!sessionService.isLogged) {
    return true;
  } else {
    router.navigateByUrl('/articles');
    return false;
  }
};
