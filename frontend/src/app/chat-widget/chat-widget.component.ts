import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-chat-widget',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './chat-widget.component.html',
  styleUrls: ['./chat-widget.component.scss']
})
export class ChatWidgetComponent {
  chatOpen = false;
  message: string = '';
  messages: string[] = [];

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
      this.messages.push(this.message);
      console.log('Message sent:', this.message);
      this.message = '';
    }
  }

}
