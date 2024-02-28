import { Component, Input, OnDestroy, OnInit, Output } from '@angular/core';
import { RouteService } from '../../core/service/route/route.service';
import { RouterLink } from '@angular/router';
import { MobileNav } from '../../interface/MobileNav.interface';
import { CommonModule } from '@angular/common';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-nav',
  standalone: true,
  imports: [RouterLink, CommonModule],
  templateUrl: './nav.component.html',
  styleUrl: './nav.component.scss',
})
export class NavComponent implements OnInit, OnDestroy {
  @Input() mobileVersionProps!: MobileNav;

  isOnArticlesPage = false;
  isOnThemePage = false;
  isOnMePage = false;

 routeSubscription!: Subscription;

  constructor(private routeService: RouteService) {}

  ngOnInit(): void {
    this.routeSubscription = this.routeService.getCurrentRoute$().subscribe((currentRoute: string) => {
      this.isOnArticlesPage = false;
      this.isOnMePage = false;
      this.isOnThemePage = false;

      // Updating navigation flags based on the current route
      if (currentRoute === '/articles') {
        this.isOnArticlesPage = true;
      }
      if (currentRoute === '/theme') {
        this.isOnThemePage = true;
      }
      if (currentRoute === '/me') {
        this.isOnMePage = true;
      }
    });
  }

  ngOnDestroy(): void {
    if (this.routeSubscription) {
      this.routeSubscription.unsubscribe();
    }
  }
}
