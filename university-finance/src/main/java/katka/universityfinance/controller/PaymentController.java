package katka.universityfinance.controller;

import katka.universityfinance.dtos.PaymentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/payments")
public class PaymentController {

  private final JmsTemplate jmsTemplate;

  @PostMapping
  public void registerPayment(@RequestBody PaymentDto payment){
    this.jmsTemplate.convertAndSend("payments", payment);
  }
}
