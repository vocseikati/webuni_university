package katka.university.ws;

import hu.webuni.university.api.ChatControllerApi;
import hu.webuni.university.api.model.ChatMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ChatController implements ChatControllerApi {

  private final SimpMessagingTemplate messagingTemplate;

  @Override //nem kell? @MessageMapping("/chat")
  public ResponseEntity<Void> onChatMessage(ChatMessage chatMessage) {
    this.messagingTemplate.convertAndSend("topic/courseChat" + chatMessage.getCourseId(),
        String.format("%s: %s", chatMessage.getSender(), chatMessage.getText()));
    return ResponseEntity.ok().build();
  }
}
