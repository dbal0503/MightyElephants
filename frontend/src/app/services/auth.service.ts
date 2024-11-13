// src/app/services/auth.service.ts
import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private signedInSubject: BehaviorSubject<boolean>;
  public signedIn$;

  constructor() {
    const initialSignInStatus = this.hasToken();
    this.signedInSubject = new BehaviorSubject<boolean>(initialSignInStatus);
    this.signedIn$ = this.signedInSubject.asObservable();
  }

  private isBrowser(): boolean {
    return typeof window !== 'undefined' && typeof localStorage !== 'undefined';
  }

  private hasToken(): boolean {
    if (this.isBrowser()) {
      return !!localStorage.getItem('id_token');
    }
    return false;
  }

  signIn(token: string, user: any) {
    if (this.isBrowser()) {
      localStorage.setItem('id_token', token);
      localStorage.setItem('user', JSON.stringify(user));
      this.signedInSubject.next(true);
    }
  }

  signOut() {
    if (this.isBrowser()) {
      localStorage.removeItem('id_token');
      localStorage.removeItem('user');
      this.signedInSubject.next(false);
    }
  }

  getUserInfo() {
    if (this.isBrowser()) {
      const user = localStorage.getItem('user');
      return user ? JSON.parse(user) : null;
    }
    return null;
  }
}
