import { Component, OnInit } from '@angular/core';
import { SessionService } from '../../core/service/session/session.service';
import { NavComponent } from '../nav/nav.component';
import { ActivatedRoute, Router } from '@angular/router';
import { RouteService } from '../../core/service/route/route.service';

@Component({
  selector: 'app-header',
  standalone: true,
  imports: [NavComponent],
  templateUrl: './header.component.html',
  styleUrl: './header.component.scss',
})
export class HeaderComponent implements OnInit {
  isLogged!: boolean;
  headerShouldBeDisplay = true;

  constructor(
    private sessionService: SessionService,
    private router: Router,
    private routeService: RouteService
  ) {}

  ngOnInit(): void {
    this.sessionService.$isLogged().subscribe({
      next: (response: boolean) => {
        this.isLogged = response;
      },
    });

    this.routeService.getCurrentRoute$().subscribe((currentRoute: string) => {
      if (currentRoute === '/' || currentRoute === '/home') {
        this.headerShouldBeDisplay = false;
      } else {
        this.headerShouldBeDisplay = true;
      }
    });
  }

  onLogoClick() {
    if (!this.isLogged) {
      this.router.navigateByUrl('/home');
    } else {
      this.router.navigateByUrl('/articles');
    }
  }
}
