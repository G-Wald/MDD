import { Router, ActivatedRoute } from '@angular/router';
import { Component, OnInit } from '@angular/core';
import { ThemeResponse } from 'src/app/interfaces/themesResponse.interface';
import { NewArticleInformation } from 'src/app/interfaces/newArticleInformation.interface';
import { ThemesService } from 'src/app/services/themes.service';
import { SessionService } from 'src/app/services/session.service';
import { ArticlesService } from 'src/app/services/articles.service';
import { AuthService } from 'src/app/services/auth.service';
import { SessionInformation } from 'src/app/interfaces/sessionInformation.interface';
import { ProfilRequest } from 'src/app/interfaces/profilRequest.interface';
import { SaveProfilResponse } from 'src/app/interfaces/SaveProfilResponse';
import { LoginResponse } from 'src/app/interfaces/loginResponse.interface';

@Component({
  selector: 'app-profil',
  templateUrl: './profil.component.html',
  styleUrls: ['./profil.component.scss']
})
export class ProfilComponent implements OnInit {
  article : NewArticleInformation;
  profil: SessionInformation;
  isRegistered: Boolean;
  constructor(private router: Router, private route: ActivatedRoute, private authService: AuthService, private themesService: ThemesService, private sessionService: SessionService, private articleService : ArticlesService) {
    this.isRegistered = false;
    this.article = new NewArticleInformation( new Array<Number>(),"","");
    this.profil = new SessionInformation("","","");
  }
  ngOnInit(): void {
    this.sessionService.checkCookie()
    this.isRegistered = true;
      this.authService.getProfil().subscribe(data => {
        this.profil = data;
    });
  }

  goBackToList() {
    this.router.navigate(['/articles']);
  }

  submitForm(){
    this.authService.saveProfil(new ProfilRequest( this.profil.username, this.profil.email)).subscribe(
      (data : SaveProfilResponse ) => {
        this.sessionService.logIn(new LoginResponse(data.token) );
      },
      (error) => {
        console.error('Erreur lors de la requête : ', error);
      }
    );
  }

  disconnect(){
    const userId = this.sessionService.logOut();
    this.router.navigate(['/']);
  }
}

