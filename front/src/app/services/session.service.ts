import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { SessionInformation } from '../interfaces/sessionInformation.interface';
import { HttpHeaders } from '@angular/common/http';
import { LoginResponse } from '../interfaces/loginResponse.interface';


@Injectable({
  providedIn: 'root'
})
export class SessionService {

  public isLogged = false;

  constructor() {
  }

  private isLoggedSubject = new BehaviorSubject<boolean>(this.isLogged);

  private token: String | null = null;

  setToken(token: String): void {
    this.token = token;
  }

  getToken(): String | null {
    return this.token;
  }

  getHeadersWithAuthorization(): HttpHeaders {
    return new HttpHeaders({
      Authorization: `Bearer ${this.token}`,
    });
  }

  public $isLogged(): Observable<boolean> {
    return this.isLoggedSubject.asObservable();
  }

  public logIn(tokenResponse: LoginResponse): void {
    this.token = tokenResponse.token;
    this.isLogged = true;
    this.next();
  }

  public logOut(): void {
    this.isLogged = false;
    this.token = "";
    this.next();
  }

  private next(): void {
    this.isLoggedSubject.next(this.isLogged);
  }
}
