import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, catchError, throwError } from 'rxjs';
import { ArticleSmallInformation } from '../interfaces/articleSmallInformation.interface';
import { ArticleInformation } from '../interfaces/articleInformation.interface';
import { NewArticleInformation } from '../interfaces/newArticleInformation.interface';
import { SessionService } from './session.service';


@Injectable({
    providedIn: 'root'
  })
  export class ArticlesService {
  
    private pathService = '/api';
  
    constructor(private httpClient: HttpClient, private sessionService: SessionService) { }
  
    public getArticles(): Observable<ArticleSmallInformation[]> {
      const headers = this.sessionService.getHeadersWithAuthorization();
      return this.httpClient.get<ArticleSmallInformation[]>(`${this.pathService}/articles`, {headers});
    }
  
    public getArticle(id: number): Observable<ArticleInformation> {
      const headers = this.sessionService.getHeadersWithAuthorization();
      return this.httpClient.get<ArticleInformation>(`${this.pathService}/article/${id}`, {headers});
    }

    public createArticle(newArticle: NewArticleInformation ): Observable<any> {
      const headers = this.sessionService.getHeadersWithAuthorization();
      return this.httpClient.post<any>(`${this.pathService}/newarticle`, newArticle, {headers})
      .pipe(
        catchError((error) => {
          console.error(error);
          return throwError(() => new HttpErrorResponse(error));
        })
      );
    }
  }