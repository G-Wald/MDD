import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ThemeResponse } from 'src/app/interfaces/themesResponse.interface';
import { NewArticleInformation } from 'src/app/interfaces/newArticleInformation.interface';
import { ThemesService } from 'src/app/services/themes.service';
import { SessionService } from 'src/app/services/session.service';
import { ArticlesService } from 'src/app/services/articles.service';

@Component({
  selector: 'app-new-article',
  templateUrl: './new-article.component.html',
  styleUrls: ['./new-article.component.scss']
})
export class NewArticleComponent implements OnInit {
  article : NewArticleInformation;
  isRegistered: Boolean;
  themes: ThemeResponse[];
  themesSelected: string[] = [];
  constructor(private router: Router, private route: ActivatedRoute, private themesService: ThemesService, private sessionService: SessionService, private articleService : ArticlesService) {
    this.isRegistered = false;
    this.themes = new Array<ThemeResponse>;
    this.article = new NewArticleInformation( new Array<Number>(),"","");
  }
  ngOnInit(): void {
    this.sessionService.checkCookie();
    this.isRegistered = true;
    this.themesService.getThemes().subscribe(data => {
      this.themes = data;
    });
  }

  goBackToList() {
    this.router.navigate(['/articles']);
  }

  submitForm(){
    this.articleService.createArticle(this.article).subscribe({
      next:(data) =>{
        console.log(data);
      },
      error:(error)=>console.log(error),
    });
    
    this.router.navigate(['/articles']);
  }
}
