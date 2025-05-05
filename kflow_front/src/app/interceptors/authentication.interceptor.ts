import { HttpErrorResponse, HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { AuthenticationService } from '../services/authentication.service';
import { catchError, mergeMap, throwError } from 'rxjs';

export const authenticationInterceptor: HttpInterceptorFn = (req, next) => {
  const authenticationService = inject(AuthenticationService);
  let user: any;
  authenticationService.connectedUser$.subscribe(value => user = value);

  if (user && !req.url.includes('authenticate')) {
    req = req.clone({
      headers: req.headers.set('Authorization', `Bearer ${user.accessToken}`)
    });
  }

  return next(req).pipe(
    catchError(error => {
      if (error instanceof HttpErrorResponse && 
          !req.url.includes('authenticate') && 
          error.status === 401) {
        return authenticationService.refresh().pipe(
          mergeMap(jwtResponse => {
            const newReq = req.clone({
              headers: req.headers.set('Authorization', `Bearer ${jwtResponse.accessToken}`)
            });
            return next(newReq);
          }),
          catchError(err => {
            authenticationService.logout();
            return throwError(() => error);
          })
        );
      }
      return throwError(() => error);
    })
  );
};
