import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CompetitionImportComponent } from './competition-import/competition-import.component';

const routes: Routes = [
  { path: 'import', component: CompetitionImportComponent }
];

@NgModule({
  imports: [
    RouterModule.forChild(routes)
  ]
})
export class CompetitionsModule { } 