import { UsersComponent } from './users/users.component';
import { StudentComponent } from './student/student.component';
import { ProgramComponent } from './program/program.component';
import { HomeComponent } from './home/home.component';
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

const routes: Routes = [
  {path: '', component: HomeComponent},
  {path: 'program', component: ProgramComponent},
  {path: 'users', component: UsersComponent},
  {path: 'student', component: StudentComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
