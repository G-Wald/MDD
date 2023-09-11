import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Route, Router } from '@angular/router';
import { ArticleSmallInformation } from 'src/app/interfaces/articleSmallInformation.interface';
import { ArticlesService } from 'src/app/services/articles.service';
import { SessionService } from 'src/app/services/session.service';

@Component({
  selector: 'app-articles',
  templateUrl: './articles.component.html',
  styleUrls: ['./articles.component.scss']
})
export class ArticlesComponent implements OnInit {
  articles: ArticleSmallInformation[]; 

  isRegistered: Boolean;

  constructor(private router: Router, private route: ActivatedRoute, private articleService: ArticlesService, private sessionService: SessionService) {
    this.isRegistered = false;
    this.articles = new Array<ArticleSmallInformation>;
  }
  ngOnInit(): void {
    //Appeler un service pour vérifier si l'utilisateur est log
    this.isRegistered = true;
    this.articleService.getArticles(this.sessionService.getSessionInformation().id != ""? parseInt(this.sessionService.getSessionInformation().id ): 40).subscribe(data => {
      this.articles = data;
    });
  }

  goToSelectedArticle(id: number) {
    this.router.navigate(['/selected-article', id]);
  }

  createNewArticle(){
    this.router.navigate(['/new-article']);
  }
}
