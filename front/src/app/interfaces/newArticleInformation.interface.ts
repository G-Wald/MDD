  export class NewArticleInformation {
    themes: Number[];
    title: string;
    content: string;

    constructor(theme : Number[], title: string , content : string) {
      this.themes = theme;
        this.title = title;
        this.content = content;
    }
}
  