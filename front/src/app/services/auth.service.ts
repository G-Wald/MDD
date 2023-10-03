import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { LoginRequest } from '../interfaces/loginRequest.interface';
import { RegisterRequest } from '../interfaces/registerRequest.interface';
import { RegisterResponse } from '../interfaces/registerResponse.interface';
import { SessionInformation } from 'src/app/interfaces/sessionInformation.interface';
import { ProfilComponent } from '../pages/profil/profil.component';
import { ProfilRequest } from '../interfaces/profilRequest.interface';
import { Injectable } from '@angular/core';
import { SessionService } from './session.service';
import { LoginResponse } from '../interfaces/loginResponse.interface';
import { SaveProfilResponse } from '../interfaces/SaveProfilResponse';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private pathService = '/api';
  
  constructor(private httpClient: HttpClient, private sessionService: SessionService) { }

  public register(registerRequest: RegisterRequest): Observable<RegisterResponse> {
    console.log(`${this.pathService}/register`)
    return this.httpClient.post<RegisterResponse>(`${this.pathService}/register`, registerRequest);
  }

  public login(loginRequest: LoginRequest): Observable<LoginResponse> {
    console.log(`${this.pathService}/login`)
    return this.httpClient.post<LoginResponse>(`${this.pathService}/login`, loginRequest);
  }

  public getProfil(): Observable<SessionInformation> {
    const headers = this.sessionService.getHeadersWithAuthorization();
    return this.httpClient.get<SessionInformation>(`${this.pathService}/profil`, {headers});
  }

  public saveProfil(profilRequest: ProfilRequest): Observable<SaveProfilResponse> {
    const headers = this.sessionService.getHeadersWithAuthorization();
    return this.httpClient.post<SaveProfilResponse>(`${this.pathService}/saveprofil`, profilRequest, {headers});
  }

}
