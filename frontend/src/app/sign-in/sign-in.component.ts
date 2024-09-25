import { Component, EventEmitter, Output, OnInit } from '@angular/core';
import { jwtDecode } from 'jwt-decode'

declare const google: any;

@Component({
  selector: 'app-sign-in',
  standalone: true,
  templateUrl: './sign-in.component.html',
  styleUrls: ['./sign-in.component.scss'],
})
export class SignInComponent implements OnInit {
  
  @Output() close = new EventEmitter<void>();
  
  ngOnInit(): void {
    this.initializeGoogleSignIn();
  }

  initializeGoogleSignIn(): void {
    google.accounts.id.initialize({
      client_id: '676832423324-ho9rcav2ddnapfo2olvl6pm509ubpcc4.apps.googleusercontent.com',
      callback: this.handleCredentialResponse.bind(this),
      
    });
    google.accounts.id.renderButton(
      document.getElementById('google-button'),
      { theme: 'outline', size: 'large' } // Customize button style
    );
  }

  handleCredentialResponse(response: any): void {
    const token = response.credential;
    console.log('Encoded JWT ID token: ' + token);
  
    const decodedToken: any = jwtDecode(token);
    console.log('Decoded ID Token:', decodedToken);
  
    // Extract user information
    const user = {
      email: decodedToken.email,
      name: decodedToken.name,
      picture: decodedToken.picture,
    };
  
    // Store the token and user info as needed
    localStorage.setItem('id_token', token);
    localStorage.setItem('user', JSON.stringify(user));
  }

  closeModal() {
    this.close.emit();
  }
}
