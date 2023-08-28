import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ArticleSmallInformation } from '../interfaces/articleSmallInformation.interface';
import { ArticleInformation } from '../interfaces/articleInformation.interface';
import { NewArticleInformation } from '../interfaces/newArticleInformation.interface';


@Injectable({
    providedIn: 'root'
  })
  export class ArticlesService {
  
    private pathService = '/api';
  
    constructor(private httpClient: HttpClient) { }
  
    public getArticles(id: number): Observable<ArticleSmallInformation[]> {
      return this.httpClient.get<ArticleSmallInformation[]>(`${this.pathService}/articles/${id}`);
    }
  
    public getArticle(id: number): Observable<ArticleInformation> {
      return this.httpClient.get<ArticleInformation>(`${this.pathService}/article/${id}`);
    }

    public createArticle(newArticle: NewArticleInformation ): Observable<ArticleInformation> {
        return this.httpClient.post<ArticleInformation>(`${this.pathService}/newArticle`, newArticle);
      }
  }