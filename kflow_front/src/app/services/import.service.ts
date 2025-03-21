import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment';
import { Observable } from 'rxjs';
import { Competition } from '../models/competition.model';

export interface ImportCompetitionDto {
  startDate: string;
  endDate: string;
  level: string;
  place: string;
  categories: ImportCategoryDto[];
}

export interface ImportCategoryDto {
  name: string;
  participants: ImportParticipantDto[];
  stages: ImportStageDto[];
}

export interface ImportParticipantDto {
  bibNb: number;
  name: string;
  club: string;
  runs: ImportRunDto[];
}

export interface ImportStageDto {
  nbRun: number;
  rules: string;
  nbCompetitor: number;
}

export interface ImportRunDto {
  duration: number;
  score: number;
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