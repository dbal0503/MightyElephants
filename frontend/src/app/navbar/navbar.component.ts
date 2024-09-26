import { Component, EventEmitter, Output, OnInit, OnDestroy } from '@angular/core';
import { RouterLink } from '@angular/router';
import { Subscription } from 'rxjs';
import { AuthService } from '../services/auth.service'; // Adjust the path as needed
import { NgIf } from '@angular/common';

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [RouterLink, NgIf],
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss'],
})
export class NavbarComponent implements OnInit, OnDestroy {
  @Output() signIn = new EventEmitter<void>();

  isSignedIn: boolean = false;
  userName: string = '';
  private authSubscription!: Subscription;

  constructor(private authService: AuthService) {}

  ngOnInit() {
    // Subscribe to the sign-in status observable
    this.authSubscription = this.authService.signedIn$.subscribe((status) => {
      this.isSignedIn = status;
      if (status) {
        const user = this.authService.getUserInfo();
        this.userName = user?.name ?? '';
      } else {
        this.userName = '';
      }
    });
  }

  ngOnDestroy() {
    // Unsubscribe to prevent memory leaks
    if (this.authSubscription) {
      this.authSubscription.unsubscribe();
    }
  }

  openSignInModal() {
    this.signIn.emit();
  }

  signOut() {
    this.authService.signOut();
  }
}
