export class Comment {
    authorUsername: string;
    message: string;

    constructor(author: string , message : string) {
        this.authorUsername = author;
        this.message = message;
    }
}