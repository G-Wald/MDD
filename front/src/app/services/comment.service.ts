import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ArticleSmallInformation } from '../interfaces/articleSmallInformation.interface';
import { ArticleInformation } from '../interfaces/articleInformation.interface';
import { CommentResponse } from '../interfaces/commentResponse.interface';
import { NewArticleInformation } from '../interfaces/newArticleInformation.interface';
import { SessionService } from './session.service';
import { CommentRequest } from '../interfaces/commentRequest.interface';


@Injectable({
    providedIn: 'root'
  })
  export class CommentService {
  
    private pathService = '/api';
  
    constructor(private httpClient: HttpClient, private sessionService: SessionService) { }
  
    public createComment(id : number, newArticle: CommentRequest ): Observable<CommentResponse> {
      const headers = this.sessionService.getHeadersWithAuthorization();
        return this.httpClient.post<CommentResponse>(`${this.pathService}/newComment/${id}`, newArticle, {headers});
      }
  }