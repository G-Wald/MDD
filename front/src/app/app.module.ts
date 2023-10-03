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
import { ArticlesComponent } from './pages/articles/articles.component';
import { ThemeComponent } from './pages/theme/theme.component';
import { ProfilComponent } from './pages/profil/profil.component';
import { NewArticleComponent } from './pages/new-article/new-article.component';
import { SelectedArticleComponent } from './pages/selected-article/selected-article.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { HttpClientModule} from '@angular/common/http';
import { MatSelectModule } from '@angular/material/select';
import { CookieService } from 'ngx-cookie-service';

@NgModule({
  declarations: [AppComponent, HomeComponent, HeaderComponent, RegisterComponent, LoginComponent, ArticlesComponent, ThemeComponent, ProfilComponent, NewArticleComponent, SelectedArticleComponent],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MatButtonModule,
    FormsModule,
    ReactiveFormsModule,
    MatFormFieldModule,
    HttpClientModule,
    MatSelectModule,
  ],
  providers: [CookieService],
  bootstrap: [AppComponent],
})
export class AppModule {}
