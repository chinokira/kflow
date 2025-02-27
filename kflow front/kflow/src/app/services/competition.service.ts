import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../environments/environment';
import { GenericService } from './generic.service';
import { Competition } from '../models/competition.model';

@Injectable({
    providedIn: 'root'
})
export class CompetitionService extends GenericService<Competition> {
    constructor(httpClient: HttpClient) {
        super(httpClient, environment.apiUrl + "competitions");
    }
}
