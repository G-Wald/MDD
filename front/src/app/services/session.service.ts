import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { SessionInformation } from '../interfaces/sessionInformation.interface';

@Injectable({
  providedIn: 'root'
})
export class SessionService {

  public isLogged = false;
  private sessionInformation: SessionInformation | null;

  constructor() {
    this.sessionInformation = null;
  }

  private isLoggedSubject = new BehaviorSubject<boolean>(this.isLogged);

  public getSessionInformation():SessionInformation{
    
    if(this.sessionInformation == null){
      return new SessionInformation("","","");
    }
    return this.sessionInformation;
  }

  public $isLogged(): Observable<boolean> {
    return this.isLoggedSubject.asObservable();
  }

  public logIn(user: SessionInformation): void {
    this.sessionInformation = user;
    this.isLogged = true;
    this.next();
  }

  public logOut(): void {
    this.isLogged = false;
    this.next();
  }

  private next(): void {
    this.isLoggedSubject.next(this.isLogged);
  }
}
