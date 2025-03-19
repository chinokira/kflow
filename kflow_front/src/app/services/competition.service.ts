import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment';
import { GenericService } from './generic.service';

export interface Competition {
  id: number;
  startDate: string;
  endDate: string;
  level: string;
  place: string;
  categories?: any[];
}

@Injectable({
  providedIn: 'root'
})
export class CompetitionService extends GenericService<Competition> {
  constructor(httpClient: HttpClient) {
    super(httpClient, `${environment.apiUrl}/competitions`);
  }
}
