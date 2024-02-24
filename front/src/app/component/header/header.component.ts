import { Component, OnInit, Renderer2, signal } from '@angular/core';
import { SessionService } from '../../core/service/session/session.service';
import { NavComponent } from '../nav/nav.component';
import { Router } from '@angular/router';
import { RouteService } from '../../core/service/route/route.service';
import { MobileNav } from '../../interface/MobileNav.interface';

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
  screenWidth!: number;
  isMobileVersion = false;

  toogleSideNav = false;

  constructor(
    private sessionService: SessionService,
    private router: Router,
    private routeService: RouteService,
    private renderer2: Renderer2
  ) {}

  navPropsDesktop: MobileNav = {
    isMobileVersion: false,
  };
  navPropsMobile: MobileNav = {
    isMobileVersion: true,
  };

  ngOnInit(): void {
    this.sessionService.$isLogged().subscribe({
      next: (response: boolean) => {
        this.isLogged = response;
      },
    });

    this.routeService.getCurrentRoute$().subscribe((currentRoute: string) => {
      // Manages header display based on route
      if (currentRoute === '/' || currentRoute === '/home') {
        this.headerShouldBeDisplay = false;
      } else {
        this.headerShouldBeDisplay = true;
      }

      this.screenWidth = window.innerWidth;

      // Handles specific scenarios for login pages
      if (currentRoute === '/login' || currentRoute === '/register') {
        // Hides header on small screens
        if (this.screenWidth < 769) {
          this.headerShouldBeDisplay = false;
        }
        // Listens to window resize events to manage header display
        this.renderer2.listen(window, 'resize', (event) => {
          this.screenWidth = window.innerWidth;
          if (this.screenWidth < 769) {
            this.headerShouldBeDisplay = false;
          } else {
            this.headerShouldBeDisplay = true;
          }
        });
      }

      // Handles specific scenarios for non-home and non-login pages
      if (
        currentRoute !== '/home' &&
        currentRoute !== '/' &&
        currentRoute !== '/login' &&
        currentRoute !== 'register'
      ) {
        // Activates mobile version on small screens
        if (this.screenWidth < 769) {
          this.isMobileVersion = true;
        }
        // Listens to window resize events to manage mobile version
        this.renderer2.listen(window, 'resize', (event) => {
          this.screenWidth = window.innerWidth;
          if (this.screenWidth < 769) {
            this.isMobileVersion = true;
          } else {
            this.isMobileVersion = false;
          }
        });
      }
    });
  }

  /**
   * Redirects to the home page or the articles page based on login status.
   */
  onLogoClick() {
    if (!this.isLogged) {
      this.router.navigateByUrl('/home');
    } else {
      this.router.navigateByUrl('/articles');
    }
  }

  /**
   * Toggles the side navigation menu.
   */
  onBurgerMenuClick() {
    this.toogleSideNav = true;
  }

  /**
   * Closes the side navigation menu.
   */
  onOverlayClick() {
    this.toogleSideNav = false;
  }
}
