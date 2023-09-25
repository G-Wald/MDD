export class CommentResponse {
    authorUsername: string;
    message: string;

    constructor(author: string , message : string) {
        this.authorUsername = author;
        this.message = message;
    }
}