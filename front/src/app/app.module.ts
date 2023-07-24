import { NgModule } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HomeComponent } from './pages/home/home.component';
import { HeaderComponent } from './pages/header/header.component';
import { RegisterComponent } from './pages/register/register.component';
import { LoginComponent } from './pages/login/login.component';
import { FormsModule } from '@angular/forms';
import { ArticlesComponent } from './pages/articles/articles.component';
import { ThemesComponent } from './pages/themes/themes.component';
import { ThemeComponent } from './pages/theme/theme.component';
import { ArticleComponent } from './pages/article/article.component';
import { ProfilComponent } from './pages/profil/profil.component';
import { NewArticleComponent } from './pages/new-article/new-article.component';
import { SelectedArticleComponent } from './pages/selected-article/selected-article.component';

@NgModule({
  declarations: [AppComponent, HomeComponent, HeaderComponent, RegisterComponent, LoginComponent, ArticlesComponent, ThemesComponent, ThemeComponent, ArticleComponent, ProfilComponent, NewArticleComponent, SelectedArticleComponent],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MatButtonModule,
    FormsModule
  ],
  providers: [],
  bootstrap: [AppComponent],
})
export class AppModule {}
