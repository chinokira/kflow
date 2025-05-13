import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment';
import { Observable } from 'rxjs';
import { Competition, Categorie } from '../models/competition-detail.model';
import { GenericService } from './generic.service';

export interface UpdateCompetitionDto {
  id: number;
  startDate: string;
  endDate: string;
  level: string;
  place: string;
  categories: Categorie[];
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

  override update(updateDto: UpdateCompetitionDto): Observable<Competition> {
    if (!updateDto.id) {
      throw new Error('Competition ID is required for update');
    }

    return this.httpClient.put<Competition>(`${this.apiUrl}/${updateDto.id}`, updateDto, { headers: this.getHeaders() });
  }

  delete(id: number): Observable<void> {
    return this.httpClient.delete<void>(`${this.apiUrl}/${id}`, { headers: this.getHeaders() });
  }

  getCompetitionWithDetails(id: number): Observable<Competition> {
    return this.httpClient.get<Competition>(`${this.apiUrl}/${id}/details`, { headers: this.getHeaders() });
  }
}
