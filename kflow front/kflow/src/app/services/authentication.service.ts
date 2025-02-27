import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable, map, tap } from 'rxjs';
import { environment } from '../../environments/environment';
import { JwtPayload, jwtDecode } from 'jwt-decode';


interface ConnectedUser {
  accessToken: string;
  refreshToken: string;
  id: number;
  name: string;
}

interface JwtResponse {
  accessToken: string;
  refreshToken: string;
}

interface JwtCustomPayload extends JwtPayload {
  username: string
}

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  connectedUser = new BehaviorSubject<ConnectedUser | undefined>(undefined);
  private url = environment.apiUrl + "authenticate";

  constructor(private httpClient: HttpClient) { }

  init() {
    const json = localStorage.getItem('user');
    if (json)
      this.connectedUser.next(JSON.parse(json));
  }

  login(email: string, password: string): Observable<void> {
    return this.httpClient.post<JwtResponse>(this.url, {
      username: email,
      password: password,
      grantType: 'password'
    }).pipe(
      tap(res => {
        const decodedAccessToken = jwtDecode<JwtCustomPayload>(res.accessToken);
        const user = {
          accessToken: res.accessToken,
          refreshToken: res.refreshToken,
          id: Number(decodedAccessToken.sub),
          name: decodedAccessToken.username
        };
        this.connectedUser.next(user);
        localStorage.setItem('user', JSON.stringify(user));
      }),
      map(res => void 0));
  }

  logout() {
    localStorage.removeItem('user');
    this.connectedUser.next(undefined);
  }

  refresh() {
    return this.httpClient.post<JwtResponse>(this.url, {
      refreshToken: this.connectedUser.value?.refreshToken,
      grantType: 'refreshToken'
    }).pipe(
      tap(res => {
        const decodedAccessToken = jwtDecode<JwtCustomPayload>(res.accessToken);
        const user = {
          accessToken: res.accessToken,
          refreshToken: res.refreshToken,
          id: Number(decodedAccessToken.sub),
          name: decodedAccessToken.username
        };
        this.connectedUser.next(user);
        localStorage.setItem('user', JSON.stringify(user));
      }));
  }

}
