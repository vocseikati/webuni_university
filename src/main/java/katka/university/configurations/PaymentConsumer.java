package katka.university.configurations;

import katka.university.services.DefaultStudentService;
import katka.universityfinance.dtos.PaymentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentConsumer {

  private final DefaultStudentService studentService;

  @JmsListener(destination = "payments", containerFactory = "myFactory")
  public void onPaymentMessage(PaymentDto paymentDto) {
    studentService.updateBalance(paymentDto.getStudentId(), paymentDto.getAmount());
  }
}
