import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment';
import { Observable } from 'rxjs';
import { Competition } from '../models/competition-detail.model';
import { GenericService } from './generic.service';

export interface UpdateCompetitionDto {
  id: number;
  startDate: string;
  endDate: string;
  level: string;
  place: string;
  categories: {
    id: number;
    name: string;
  }[];
}

@Injectable({
  providedIn: 'root'
})
export class CompetitionService extends GenericService<Competition> {
  private readonly apiUrl = `${environment.apiUrl}/competitions`;

  constructor(override readonly httpClient: HttpClient) {
    super(httpClient, environment.apiUrl + "/competitions");
  }

  private getHeaders(): HttpHeaders {
    return new HttpHeaders()
      .set('Content-Type', 'application/json')
      .set('Accept', 'application/json');
  }

  override findAll(): Observable<Competition[]> {
    return this.httpClient.get<Competition[]>(this.apiUrl, { headers: this.getHeaders() });
  }

  override findById(id: number): Observable<Competition> {
    return this.httpClient.get<Competition>(`${this.apiUrl}/${id}`, { headers: this.getHeaders() });
  }

  create(competition: Competition): Observable<Competition> {
    return this.httpClient.post<Competition>(this.apiUrl, competition, { headers: this.getHeaders() });
  }

  override update(t: Partial<Competition>): Observable<Competition> {
    if (!t.id) {
      throw new Error('Competition ID is required for update');
    }

    const updateDto: UpdateCompetitionDto = {
      id: t.id,
      startDate: t.startDate || '',
      endDate: t.endDate || '',
      level: t.level || '',
      place: t.place || '',
      categories: (t.categories || []).map(cat => ({
        id: cat.id,
        name: cat.name
      }))
    };

    return this.httpClient.put<Competition>(`${this.apiUrl}/${t.id}`, updateDto, { headers: this.getHeaders() });
  }

  delete(id: number): Observable<void> {
    return this.httpClient.delete<void>(`${this.apiUrl}/${id}`, { headers: this.getHeaders() });
  }

  getCompetitionWithDetails(id: number): Observable<Competition> {
    return this.httpClient.get<Competition>(`${this.apiUrl}/${id}/details`, { headers: this.getHeaders() });
  }
}
