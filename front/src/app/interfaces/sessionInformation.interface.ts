
  export class SessionInformation {
    username: string;
    email: string;
    error: string;

    constructor(username: string , email : string , error : string) {
        this.username = username;
        this.email = email;
        this.error = error;
    }
}
  