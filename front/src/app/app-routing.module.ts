import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { BrowserModule } from '@angular/platform-browser';
import { HomeComponent } from './pages/home/home.component';
import { LoginComponent } from './pages/login/login.component';
import { RegisterComponent } from './pages/register/register.component';
import { ArticlesComponent } from './pages/articles/articles.component';
import { ThemesComponent } from './pages/themes/themes.component';
import { ProfilComponent } from './pages/profil/profil.component';
import { SelectedArticleComponent } from './pages/selected-article/selected-article.component';

const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'profil', component: ProfilComponent },
  { path: 'articles', component: ArticlesComponent },
  { path: 'new-article', component: ArticlesComponent },
  { path: 'selected-article/:id', component: SelectedArticleComponent },
  { path: 'themes', component: ThemesComponent }
];

@NgModule({
    imports: [
    BrowserModule,
    RouterModule.forRoot(routes),
  ],
  exports: [RouterModule],
})
export class AppRoutingModule {}
