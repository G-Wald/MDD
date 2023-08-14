import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Route, Router } from '@angular/router';

@Component({
  selector: 'app-articles',
  templateUrl: './articles.component.html',
  styleUrls: ['./articles.component.scss']
})
export class ArticlesComponent implements OnInit {

  isRegistered: Boolean;

  constructor(private router: Router, private route: ActivatedRoute) {
    this.isRegistered = false
  }
  ngOnInit(): void {
    //Appeler un service pour vérifier si l'utilisateur est log
    this.isRegistered = true;
  }

}
