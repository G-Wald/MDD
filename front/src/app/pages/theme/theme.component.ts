import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ThemeResponse } from 'src/app/interfaces/themesResponse.interface';
import { SessionService } from 'src/app/services/session.service';
import { ThemesService } from 'src/app/services/themes.service';

@Component({
  selector: 'app-theme',
  templateUrl: './theme.component.html',
  styleUrls: ['./theme.component.scss']
})
export class ThemeComponent implements OnInit {

  themes: ThemeResponse[];
  isRegistered: Boolean;

  constructor(private router: Router, private route: ActivatedRoute, private themesService: ThemesService, private sessionService: SessionService) {
    this.isRegistered = false;
    this.themes = new Array<ThemeResponse>;
  }
  ngOnInit(): void {
    this.sessionService.checkCookie();
    this.isRegistered = true;
    this.themesService.getThemes().subscribe(data => {
      this.themes = data;
    });

  }

  toggleAbonnement(id: number) {
    const indice = this.themes.findIndex(objet => objet.id === id);
      this.themesService.subscribe(id).subscribe(
        (data) => {
          this.themes[indice].isSubscribe = !this.themes[indice].isSubscribe
        },
        (error) => {
          console.error('Erreur lors de la requête : ', error);
        }
      );
  }

}


