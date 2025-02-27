import { Injectable } from '@angular/core';
import { User } from '../models/user.model';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../environments/environment';
import { GenericService } from './generic.service';

@Injectable({
  providedIn: 'root'
})
export class UserService extends GenericService<User> {

  constructor(httpClient: HttpClient) {
    super(httpClient, environment.apiUrl + "users");
   }
}
