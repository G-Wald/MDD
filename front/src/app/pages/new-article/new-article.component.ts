import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-new-article',
  templateUrl: './new-article.component.html',
  styleUrls: ['./new-article.component.scss']
})
export class NewArticleComponent implements OnInit {

  isRegistered: Boolean;

  constructor(private router: Router, private route: ActivatedRoute) {
    this.isRegistered = false
  }
  ngOnInit(): void {
    //Appeler un service pour vérifier si l'utilisateur est log
    this.isRegistered = true;
  }

}
