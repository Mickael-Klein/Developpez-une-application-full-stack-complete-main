import { HttpHandlerFn, HttpHeaders, HttpRequest } from '@angular/common/http';
import { inject } from '@angular/core';
import { SessionService } from '../core/service/session/session.service';

/**
 * Interceptor function to add JWT token to the headers of HTTP requests,
 * except for requests to authentication-related endpoints.
 * @param req - The HTTP request being intercepted.
 * @param next - The HTTP handler for the intercepted request.
 * @returns The HTTP request with JWT token added to the headers.
 */
export function jwtInterceptor(req: HttpRequest<unknown>, next: HttpHandlerFn) {
  if (!req.url.includes('/auth')) {
    const jwt = localStorage.getItem('jwt');
    const headers = new HttpHeaders().append('Authorization', `Bearer ${jwt}`);
    req = req.clone({ headers });
  }

  return next(req);
}
