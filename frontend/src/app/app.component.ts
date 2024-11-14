import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterOutlet } from '@angular/router';
import { NavbarComponent } from './navbar/navbar.component';
import { SignInComponent } from './sign-in/sign-in.component';
import { QuoteRequestComponent } from './quote/quote-request.component';
import { ChatWidgetComponent } from './chat-widget/chat-widget.component';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [
    CommonModule,
    RouterOutlet,
    NavbarComponent,
    SignInComponent,
    QuoteRequestComponent,
    ChatWidgetComponent,
  ],
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'],
})
export class AppComponent {
  showSignInModal = false;

  openSignInModal() {
    this.showSignInModal = true;

  }

  closeSignInModal() {
    this.showSignInModal = false;
  }
}
