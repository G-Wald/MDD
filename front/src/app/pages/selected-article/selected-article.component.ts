import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-selected-article',
  templateUrl: './selected-article.component.html',
  styleUrls: ['./selected-article.component.scss']
})
export class SelectedArticleComponent implements OnInit {

  private idArticle: string;
  isRegistered: Boolean;

  constructor(private router :Router, private route: ActivatedRoute) {
    this.idArticle = "";
    this.isRegistered = false;
   }

  ngOnInit(): void {
    this.idArticle = this.route.snapshot.params['id'];
    this.isRegistered = true;

  }

}
