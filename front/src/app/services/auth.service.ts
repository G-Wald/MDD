import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { LoginRequest } from '../interfaces/loginRequest.interface';
import { RegisterRequest } from '../interfaces/registerRequest.interface';
import { RegisterResponse } from '../interfaces/registerResponse.interface';
import { SessionInformation } from 'src/app/interfaces/sessionInformation.interface';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private pathService = '/api';

  constructor(private httpClient: HttpClient) { }

  public register(registerRequest: RegisterRequest): Observable<RegisterResponse> {
    console.log(`${this.pathService}/register`)
    return this.httpClient.post<RegisterResponse>(`${this.pathService}/register`, registerRequest);
  }

  public login(loginRequest: LoginRequest): Observable<SessionInformation> {
    console.log(`${this.pathService}/login`)
    return this.httpClient.post<SessionInformation>(`${this.pathService}/login`, loginRequest);
  }
}
