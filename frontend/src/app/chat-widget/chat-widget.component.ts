import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ChatResponse } from './chat-response.model';

@Component({
  selector: 'app-chat-widget',
  standalone: true,
  imports: [CommonModule, FormsModule, HttpClientModule],
  templateUrl: './chat-widget.component.html',
  styleUrls: ['./chat-widget.component.scss']
})
export class ChatWidgetComponent {
  chatOpen = false;
  message: string = '';
  messages: string[] = [];

  constructor(private http: HttpClient) {}
  toggleChat() {
    this.chatOpen = !this.chatOpen;
  }
  closeChat(event: Event) {
    event.stopPropagation()
    this.chatOpen = false;
  }

  preventClose(event: Event) {
    event.stopPropagation();
  }

  sendMessage() {
    if (this.message.trim()) {
      this.messages.push(`You: ${this.message}`);
      this.getBotResponse(this.message).subscribe((response) => {
              this.messages.push(`Bot: ${response.reply}`);
            });
      console.log('Message sent:', this.message);
      this.message = '';
    }
  }
  getBotResponse(message: string): Observable<any> {
    const apiUrl = 'http://localhost:8080/api/chat';
    const body = { message };

    return this.http.post<ChatResponse>(apiUrl, body);
  }

}
