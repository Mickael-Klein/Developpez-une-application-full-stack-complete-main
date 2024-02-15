import { Component, OnInit } from '@angular/core';
import { RouteService } from '../../core/service/route/route.service';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-nav',
  standalone: true,
  imports: [RouterLink],
  templateUrl: './nav.component.html',
  styleUrl: './nav.component.scss',
})
export class NavComponent implements OnInit {
  isOnArticlesPage = false;
  isOnThemePage = false;
  isOnMePage = false;

  constructor(private routeService: RouteService) {}

  ngOnInit(): void {
    this.routeService.getCurrentRoute$().subscribe((currentRoute: string) => {
      this.isOnArticlesPage = false;
      this.isOnMePage = false;
      this.isOnThemePage = false;

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
}
