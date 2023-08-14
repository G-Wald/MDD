import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-profil',
  templateUrl: './profil.component.html',
  styleUrls: ['./profil.component.scss']
})
export class ProfilComponent implements OnInit {

  isRegistered: Boolean;

  constructor(private router: Router, private route: ActivatedRoute) {
    this.isRegistered = false
  }
  ngOnInit(): void {
    //Appeler un service pour vérifier si l'utilisateur est log
    this.isRegistered = true;
  }

}
