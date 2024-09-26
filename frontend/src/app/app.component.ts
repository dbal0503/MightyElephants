import { ChangeDetectorRef, Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterOutlet } from '@angular/router';
import { NavbarComponent } from './navbar/navbar.component';
import { SignInComponent } from './sign-in/sign-in.component';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [
    CommonModule,
    RouterOutlet,
    NavbarComponent,
    SignInComponent, 
  ],
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'],
})
export class AppComponent {
  
  public showSignInModal = false;
  
  constructor(private ref: ChangeDetectorRef) {}

  openSignInModal() {
    this.showSignInModal = true;
  }

  closeSignInModal() {
    this.showSignInModal = false;
    this.ref.detectChanges();
  }
}
