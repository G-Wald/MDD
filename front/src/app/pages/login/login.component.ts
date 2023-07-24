import { Component, OnInit } from '@angular/core';
import { NgForm} from '@angular/forms';


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  username!: string;
  email!: string;
  password!: string;

  constructor() { }

  ngOnInit(): void {
  }


  submitForm( form: NgForm){
    console.log(form.value);
    
  }
}
