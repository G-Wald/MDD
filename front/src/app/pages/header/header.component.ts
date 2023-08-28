import { Component, Input, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { SessionService } from 'src/app/services/session.service';


@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit {
  isLogged = false;
  @Input() isRegistered: Boolean;

  constructor(private router: Router, private route: ActivatedRoute, private sessionService : SessionService) {
    this.isRegistered = false
  }

  ngOnInit() {
    this.router.events.subscribe((event) => {
      // Vérifiez si l'utilisateur est sur la page principale
      //const isMainPage = this.route.snapshot.firstChild?.routeConfig?.path === '';
  
      // Mettez à jour l'état de l'affichage de l'en-tête
      //this.showHeader = !isMainPage;
    });
    this.isLogged = this.sessionService.isLogged;
  }

}
