import { Component, OnInit } from '@angular/core';
import {Router} from '@angular/router';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-selected-article',
  templateUrl: './selected-article.component.html',
  styleUrls: ['./selected-article.component.scss']
})
export class SelectedArticleComponent implements OnInit {

  private idArticle: string;

  constructor(private router :Router, private route: ActivatedRoute) {
    this.idArticle = "";
   }

  ngOnInit(): void {
    this.idArticle = this.route.snapshot.params['id'];
  }

}
