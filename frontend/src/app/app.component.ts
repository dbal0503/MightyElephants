import { ChangeDetectorRef, Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterOutlet } from '@angular/router';
import { NavbarComponent } from './navbar/navbar.component';
import { SignInComponent } from './sign-in/sign-in.component';
import { QuoteRequestComponent } from './quote/quote-request.component';
import { environment } from '../environments/environment';
import { ChatWidgetComponent } from './chat-widget/chat-widget.component';
import { ShippingLabelComponent} from "./shippinglabel/shippinglabel.component";

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
    ShippingLabelComponent
  ],
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'],
})
export class AppComponent {
  public showSignInModal = false;

  openSignInModal() {
    this.showSignInModal = true;

  }

  closeSignInModal() {
    this.showSignInModal = false;
  }
  ngOnInit() {
    if (typeof window !== 'undefined') {
      this.loadGoogleMapsAPI();
    }
  }


  loadGoogleMapsAPI() {
    if (typeof document !== 'undefined') {
      const script = document.createElement('script');
      script.src = `https://maps.googleapis.com/maps/api/js?key=${environment.API_KEY}&libraries=places`;
      script.async = true;
      script.defer = true;
      document.body.appendChild(script);
    }
  }

}
