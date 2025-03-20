import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment';
import { Observable } from 'rxjs';
import { Categorie } from '../models/competition-detail.model';

@Injectable({
    providedIn: 'root'
})
export class CategorieService {
    private readonly apiUrl = `${environment.apiUrl}/categories`;

    constructor(private readonly httpClient: HttpClient) {}

    getParticipants(id: number): Observable<Categorie> {
        return this.httpClient.get<Categorie>(`${this.apiUrl}/${id}/participants`);
    }
}
