import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment';
import { Observable } from 'rxjs';
import { Run } from '../models/competition-detail.model';

@Injectable({
    providedIn: 'root'
})
export class ParticipantService {
    private readonly apiUrl = `${environment.apiUrl}/participants`;

    constructor(private readonly httpClient: HttpClient) {}

    getRuns(id: number): Observable<Run[]> {
        return this.httpClient.get<Run[]>(`${this.apiUrl}/${id}/runs`);
    }
}

