import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { HttpHeaders } from '@angular/common/http';
import { LoginResponse } from '../interfaces/loginResponse.interface';
import { CookieService } from 'ngx-cookie-service';
import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { IsTokenOkReponse } from '../interfaces/isTokenOkResponse';

@Injectable({
  providedIn: 'root'
})
export class SessionService {

  public isLogged = false;

  constructor(private cookie: CookieService,
    private router: Router,
    private httpClient: HttpClient) {
  }

  private isLoggedSubject = new BehaviorSubject<boolean>(this.isLogged);

  getHeadersWithAuthorization(): HttpHeaders {
    let token = this.cookie.get('mddapp');

    return new HttpHeaders({
      Authorization: `Bearer ${token}`,
    });
  }

  public checkCookie(): void{
    let token = this.cookie.get('mddapp');

    if(token == null || token ==""){
      this.router.navigate(['/']);
      return;
    }
    const headers = new HttpHeaders({Authorization: `Bearer ${token}`});
    this.httpClient.get<IsTokenOkReponse>(`/api/istokenok`,{headers}).subscribe((response : IsTokenOkReponse) => {
        if(response.isTokenOk){
          return;
        }
        this.router.navigate(['/']);
    });;
  }


  public $isLogged(): Observable<boolean> {
    return this.isLoggedSubject.asObservable();
  }

  public logIn(tokenResponse: LoginResponse): void {
    let token = this.cookie.get('mddapp');

    if(token != null){
      this.cookie.delete('mddapp');
    }

    this.cookie.set('mddapp', tokenResponse.token, 1);
    this.isLogged = true;
    this.next();
  }

  public logOut(): void {
    this.cookie.delete('mddapp');
    this.isLogged = false;
    this.next();
  }

  private next(): void {
    this.isLoggedSubject.next(this.isLogged);
  }
}
