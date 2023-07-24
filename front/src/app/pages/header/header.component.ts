import { Component, Input, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';


@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit {

  @Input() showHeader: Boolean;

  constructor(private router: Router, private route: ActivatedRoute) {
    this.showHeader = false
  }

  ngOnInit() {
    this.router.events.subscribe((event) => {
      // Vérifiez si l'utilisateur est sur la page principale
      //const isMainPage = this.route.snapshot.firstChild?.routeConfig?.path === '';
  
      // Mettez à jour l'état de l'affichage de l'en-tête
      //this.showHeader = !isMainPage;
    });
  }

}
