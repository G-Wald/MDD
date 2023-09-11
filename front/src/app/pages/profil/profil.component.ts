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
    this.profil = new SessionInformation("","","","");
  }
  ngOnInit(): void {
    //Appeler un service pour vérifier si l'utilisateur est log
    this.isRegistered = true;
      this.authService.getProfil(this.sessionService.getSessionInformation().id != ""? parseInt(this.sessionService.getSessionInformation().id ): 40).subscribe(data => {
        this.profil = data;
    });
  }

  goBackToList() {
    this.router.navigate(['/articles']);
  }

  submitForm(){
    const userId = this.sessionService.getSessionInformation().id != ""? parseInt(this.sessionService.getSessionInformation().id) : 40;
    this.authService.saveProfil(new ProfilRequest( this.profil.username, this.profil.email))
  }

  disconnect(){
    const userId = this.sessionService.logOut();
    this.router.navigate(['/']);
  }
}

