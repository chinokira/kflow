import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment';
import { Observable } from 'rxjs';
import { Competition } from '../models/competition-detail.model';

@Injectable({
  providedIn: 'root'
})
export class CompetitionService {
  private apiUrl = `${environment.apiUrl}/competitions`;

  constructor(private httpClient: HttpClient) {}

  findAll(): Observable<Competition[]> {
    return this.httpClient.get<Competition[]>(this.apiUrl);
  }

  findById(id: number): Observable<Competition> {
    return this.httpClient.get<Competition>(`${this.apiUrl}/${id}/details`);
  }

  create(competition: Competition): Observable<Competition> {
    return this.httpClient.post<Competition>(this.apiUrl, competition);
  }

  update(id: number, competition: Competition): Observable<Competition> {
    return this.httpClient.put<Competition>(`${this.apiUrl}/${id}`, competition);
  }

  delete(id: number): Observable<void> {
    return this.httpClient.delete<void>(`${this.apiUrl}/${id}`);
  }
}
