import { CommentResponse } from "./commentResponse.interface"

export interface ArticleInformation {
    id:number;
    title:String;
    createdAt:Date;
    authorUsername:String;
    content:string;
    themes: String[];
    comments: Array<CommentResponse>;
  }
  