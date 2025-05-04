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
  role: string;
}

interface JwtResponse {
  accessToken: string;
  refreshToken: string;
}

interface JwtCustomPayload extends JwtPayload {
  username: string;
  role: string;
}

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {
  private readonly connectedUser = new BehaviorSubject<ConnectedUser | undefined>(undefined);
  private readonly url = environment.apiUrl + "/authenticate";

  constructor(private readonly httpClient: HttpClient) {
    this.init();
  }

  init() {
    const json = localStorage.getItem('user');
    if (json) {
      const user = JSON.parse(json);
      const decodedToken = jwtDecode<JwtCustomPayload>(user.accessToken);
      const expirationDate = new Date(decodedToken.exp! * 1000);
      
      if (expirationDate > new Date()) {
        this.connectedUser.next(user);
      } else {
        this.refresh().subscribe({
          next: () => console.log('Token rafraîchi avec succès'),
          error: () => {
            console.log('Impossible de rafraîchir le token, déconnexion');
            this.logout();
          }
        });
      }
    }
  }

  login(email: string, password: string): Observable<void> {
    return this.httpClient.post<JwtResponse>(this.url, {
      username: email,
      password: password,
      grantType: 'password'
    }).pipe(
      tap(res => {
        const decodedAccessToken = jwtDecode<JwtCustomPayload>(res.accessToken);
        console.log('JWT decoded:', decodedAccessToken);
        const user = {
          accessToken: res.accessToken,
          refreshToken: res.refreshToken,
          id: Number(decodedAccessToken.sub),
          name: decodedAccessToken.username,
          role: decodedAccessToken.role
        };
        console.log('User created:', user);
        this.connectedUser.next(user);
        localStorage.setItem('user', JSON.stringify(user));
      }),
      map(res => void 0));
  }

  logout() {
    localStorage.removeItem('user');
    this.connectedUser.next(undefined);
  }

  refresh(): Observable<JwtResponse> {
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
          name: decodedAccessToken.username,
          role: decodedAccessToken.role
        };
        this.connectedUser.next(user);
        localStorage.setItem('user', JSON.stringify(user));
      }));
  }

  get connectedUser$(): Observable<ConnectedUser | undefined> {
    return this.connectedUser.asObservable();
  }

  get isAuthenticated(): boolean {
    return !!this.connectedUser.value;
  }

  get isAdmin(): boolean {
    return this.connectedUser.value?.role === 'ADMIN';
  }
}
