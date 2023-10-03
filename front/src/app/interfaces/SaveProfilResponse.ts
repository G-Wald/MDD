export class SaveProfilResponse {
    username: string;
    email: string;
    token: string;
    error: string;

    constructor(username: string , email : string,token : string, error : string) {
        this.username = username;
        this.email = email;
        this.token = token;
        this.error = error;
    }
}