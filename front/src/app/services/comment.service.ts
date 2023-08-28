import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ArticleSmallInformation } from '../interfaces/articleSmallInformation.interface';
import { ArticleInformation } from '../interfaces/articleInformation.interface';
import { Comment } from '../interfaces/comment.interface';
import { NewArticleInformation } from '../interfaces/newArticleInformation.interface';


@Injectable({
    providedIn: 'root'
  })
  export class CommentService {
  
    private pathService = '/api';
  
    constructor(private httpClient: HttpClient) { }
  
    public createComment(id : number, newArticle: Comment ): Observable<Comment> {
        return this.httpClient.post<Comment>(`${this.pathService}/newComment/${id}`, newArticle);
      }
  }