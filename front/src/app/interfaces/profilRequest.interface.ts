export interface ProfilRequest {
    username: string;
    email: string;
}


export class ProfilRequest {
    username: string;
    email: string;
    constructor( username: string, email: string) {
        this.username = username;
        this.email = email;
    }
}
  