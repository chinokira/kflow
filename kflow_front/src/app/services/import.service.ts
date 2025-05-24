import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment';
import { Observable } from 'rxjs';
import { Competition } from '../models/competition.model';
import { Categorie } from '../models/categorie.model';
import { Participant } from '../models/participant.model';
import { Stage } from '../models/stage.model';
import { Run } from '../models/run.model';

export interface ImportCompetitionDto {
  startDate: string;
  endDate: string;
  level: string;
  place: string;
  categories: Categorie[];
}

@Injectable({
  providedIn: 'root'
})
export class ImportService {
  private readonly apiUrl = `${environment.apiUrl}/api/import`;

  constructor(private readonly http: HttpClient) { }

  validateImport(importData: ImportCompetitionDto): Observable<string[]> {
    return this.http.post<string[]>(`${this.apiUrl}/validate`, importData);
  }

  importCompetition(importData: ImportCompetitionDto): Observable<Competition> {
    return this.http.post<Competition>(`${this.apiUrl}/competition`, importData);
  }
} 