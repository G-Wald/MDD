import { Component, Input, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { SessionService } from 'src/app/services/session.service';
import { MatDialog } from '@angular/material/dialog';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit {
  isLogged = false;
  menuOpen = false;
  @Input() isRegistered: Boolean;

  constructor(private dialog: MatDialog,private router: Router, private route: ActivatedRoute, private sessionService : SessionService) {
    this.isRegistered = false
  }

  ngOnInit() {
    this.router.events.subscribe((event) => {
    });
    this.isLogged = this.sessionService.isLogged;
  }


  getTitleStyle(routeName: string) {
    const currentRoute = this.route.snapshot.routeConfig?.path;
    return {
      color: currentRoute === routeName ? 'purple' : 'black',
    };
  }
  openMenu() {
    this.menuOpen = true;
  }

  closeMenu() {
    this.menuOpen = false;
  }
}
