
  export class SessionInformation {
    id : string;
    username: string;
    email: string;
    error: string;

    constructor(id : string, username: string , email : string , error : string) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.error = error;
    }
}
  