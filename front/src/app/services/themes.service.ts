import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ArticleSmallInformation } from '../interfaces/articleSmallInformation.interface';
import { UserIdRequest } from '../interfaces/userIdRequest.interface';
import { ArticleInformation } from '../interfaces/articleInformation.interface';
import { Comment } from '../interfaces/comment.interface';
import { NewArticleInformation } from '../interfaces/newArticleInformation.interface';
import { ThemeResponse } from '../interfaces/themesResponse.interface';


@Injectable({
    providedIn: 'root'
  })
  export class ThemesService {
  
    private pathService = '/api';
  
    constructor(private httpClient: HttpClient) { }
  
    public getThemes(id : number ): Observable<ThemeResponse[]> {
        return this.httpClient.get<ThemeResponse[]>(`${this.pathService}/themes/${id}`);
    }

    public subscribe(id : number, userId: UserIdRequest ): Observable<void> {
        return this.httpClient.post<void>(`${this.pathService}/subscribe/${id}`, userId);
    }

    public unsubscribe(id : number, userId: UserIdRequest ): Observable<void> {
        return this.httpClient.post<void>(`${this.pathService}/unsubscribe/${id}`, userId);
    }

  }