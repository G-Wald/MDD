import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ArticleSmallInformation } from '../interfaces/articleSmallInformation.interface';
import { ArticleInformation } from '../interfaces/articleInformation.interface';
import { NewArticleInformation } from '../interfaces/newArticleInformation.interface';
import { ThemeResponse } from '../interfaces/themesResponse.interface';
import { SessionService } from './session.service';


@Injectable({
    providedIn: 'root'
  })
  export class ThemesService {
  
    private pathService = '/api';
  
    constructor(private httpClient: HttpClient, private sessionService: SessionService) { }
  
    public getThemes(): Observable<ThemeResponse[]> {
        const headers = this.sessionService.getHeadersWithAuthorization();
        return this.httpClient.get<ThemeResponse[]>(`${this.pathService}/themes`, {headers});
    }

    public subscribe(id : number): Observable<any> {
        const headers = this.sessionService.getHeadersWithAuthorization();
        console.log(headers)
        console.log(`${this.pathService}/subscribe/${id}`)
        return this.httpClient.put<any>(`${this.pathService}/subscribe/${id}`,"" ,{headers});
    }
//changer en put
    public unsubscribe(id : number): Observable<void> {
        const headers = this.sessionService.getHeadersWithAuthorization();
        console.log(headers)
        console.log(`${this.pathService}/subscribe/${id}`)
        return this.httpClient.delete<void>(`${this.pathService}/unsubscribe/${id}`, {headers});
    }

  }