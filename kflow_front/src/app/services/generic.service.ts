import { HttpClient } from '@angular/common/http';
import { Observable, delay } from 'rxjs';


export abstract class GenericService<T extends {id: number}> {

  constructor(
    protected httpClient: HttpClient,
    protected url: string) { }

  findAll(): Observable<T[]> {
    return this.httpClient.get<T[]>(this.url).pipe(delay(1000));
  }

  findById(id: number): Observable<T> {
    return this.httpClient.get<T>(this.url + "/" + id).pipe(delay(1000));
  }

  save(t: Partial<T>): Observable<T> {
    return this.httpClient.post<T>(this.url, t).pipe(delay(1000));
  }

  update(t: Partial<T>): Observable<T> {
    return this.httpClient.put<T>(this.url + "/" + t.id, t).pipe(delay(1000));
  }

  deleteById(id: number): Observable<void> {
    return this.httpClient.delete<void>(this.url + "/" + id).pipe(delay(1000));
  }
}
