import { Injectable } from '@angular/core';
import { NavigationEnd, Router } from '@angular/router';
import { BehaviorSubject, filter } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class RouteService {
  private currentRouteSubject: BehaviorSubject<string>;

  constructor(private router: Router) {
    this.currentRouteSubject = new BehaviorSubject<string>(
      this.getCurrentRoute()
    );

    this.router.events
      .pipe(filter((event) => event instanceof NavigationEnd))
      .subscribe(() => {
        const currentRoute = this.getCurrentRoute();
        this.currentRouteSubject.next(currentRoute);
      });
  }

  getCurrentRoute(): string {
    return this.router.routerState.snapshot.url;
  }

  getCurrentRoute$() {
    return this.currentRouteSubject.asObservable();
  }
}
