import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';
import { RegisterRequest } from '../../interfaces/registerRequest.interface';
import { ReactiveFormsModule } from '@angular/forms'; // Importez ReactiveFormsModule depuis @angular/forms

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent {

  public onError = false;

  form : FormGroup;

  constructor(private authService: AuthService,
    private fb: FormBuilder,
    private router: Router) { 
      this.form = this.fb.group({
        email: [
          '',
          [
            Validators.required,
            Validators.email
          ]
        ],
        username: [
          '',
          [
            Validators.required,
            Validators.min(3),
            Validators.max(40)
          ]
        ],
        password: [
          '',
          [
            Validators.required,
            Validators.min(3),
            Validators.max(255)
          ]
        ]
      });

    }

  public submitForm(): void {
    const registerRequest = this.form.value as RegisterRequest;
    this.onError = false;

    Object.keys(this.form.controls).forEach((v)=>{
      var input = this.form.controls[v]
      if(input.status == "INVALID"){
        this.onError = true;
        }
    })

    if(this.onError){
      return;
    }

    this.authService.register(registerRequest).subscribe({
        next: (_: void) => this.router.navigate(['/login']),
        error: _ => this.onError = true,
      }
    );
  }

  

}
