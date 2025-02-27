import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../environments/environment';
import { GenericService } from './generic.service';
import { Stage } from '../models/stage.model';

@Injectable({
    providedIn: 'root'
})
export class StageService extends GenericService<Stage> {
    constructor(httpClient: HttpClient) {
        super(httpClient, environment.apiUrl + "stages");
    }
}
