import { Component } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { SessionInformation } from 'src/app/interfaces/sessionInformation.interface';
import { SessionService } from 'src/app/services/session.service';
import { LoginRequest } from '../../interfaces/loginRequest.interface';
import { AuthService } from '../../services/auth.service';
import { LoginResponse } from '../../interfaces/loginResponse.interface';
import { CookieService } from 'ngx-cookie-service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent {
   
  public hide = true;
  errorMessage = '';

  public form = this.fb.group({
    usernameOrEmail: [
      '',
      [
        Validators.required,
      ]
    ],
    password: [
      '',
      [
        Validators.required,
        Validators.min(3)
      ]
    ]
  });

  constructor(private authService: AuthService,
              private fb: FormBuilder,
              private router: Router,
              private sessionService: SessionService,
              private cookie: CookieService) {
  }

  public submitForm(): void {
    const loginRequest = this.form.value as LoginRequest;
    this.authService.login(loginRequest).subscribe({
      next: (response: LoginResponse) => {
        this.sessionService.logIn(response);
        this.router.navigate(['/articles']);
      },
      error: error =>{
        if (error.error &&  error.error.error) {
          this.errorMessage = error.error.error;
        } else {
          this.errorMessage = 'Une erreur s\'est produite lors de la connexion.';
        }
      } 
    });
  }
  goBackToList() {
    this.router.navigate(['/']);
  }
}
