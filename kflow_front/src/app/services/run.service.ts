import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../environments/environment';
import { GenericService } from './generic.service';
import { Run } from '../models/run.model';

@Injectable({
    providedIn: 'root'
})
export class RunService extends GenericService<Run> {
    constructor(httpClient: HttpClient) {
        super(httpClient, environment.apiUrl + "runs");
    }
}
