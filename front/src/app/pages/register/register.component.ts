import { Component, OnInit } from '@angular/core';
import { NgForm} from '@angular/forms';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent implements OnInit {

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
