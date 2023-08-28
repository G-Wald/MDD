import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ArticleInformation } from 'src/app/interfaces/articleInformation.interface';
import { NewArticleInformation } from 'src/app/interfaces/newArticleInformation.interface';
import { Comment } from 'src/app/interfaces/comment.interface';
import { ArticlesService } from 'src/app/services/articles.service';
import { CommentService } from 'src/app/services/comment.service';
import { SessionService } from 'src/app/services/session.service';



@Component({
  selector: 'app-selected-article',
  templateUrl: './selected-article.component.html',
  styleUrls: ['./selected-article.component.scss']
})
export class SelectedArticleComponent implements OnInit {

  isRegistered: Boolean;
  article!: ArticleInformation;
  comments: Comment[] = [];
  newComment: string = '';

   constructor(
    private route: ActivatedRoute,
    private router: Router,
    private articleService: ArticlesService,
    private commentService: CommentService,
    private sessionService: SessionService,
  ) {
    this.isRegistered = true;

  }

  ngOnInit(): void {
    this.isRegistered = true;
    this.route.params.subscribe(params => {
      const articleId = +params['id'];
      this.articleService.getArticle(articleId).subscribe(data => {
        this.article = data;
      });

    });
  }

    goBackToList() {
      this.router.navigate(['/articles']);
    }
  
    addComment() {
      if (this.newComment && this.sessionService.getSessionInformation() != null) {
        let comment : Comment = new Comment(this.sessionService.getSessionInformation().username , this.newComment);
        this.commentService.createComment(this.article.id, comment ).subscribe((commentParam : Comment) => {
          
          this.article.comments.push(commentParam);
          this.newComment = ''; 
        });
      }
    }

}