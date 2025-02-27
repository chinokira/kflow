import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../environments/environment';
import { GenericService } from './generic.service';
import { Categorie } from '../models/categorie.model';

@Injectable({
    providedIn: 'root'
})
export class CategorieService extends GenericService<Categorie> {
    constructor(httpClient: HttpClient) {
        super(httpClient, environment.apiUrl + "users");
    }
}
