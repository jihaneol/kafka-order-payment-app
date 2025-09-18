package sw.sustainable.springlabs.fpay.representation.in.web;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sw.sustainable.springlabs.fpay.application.port.in.PaymentSettlementsUseCase;
import sw.sustainable.springlabs.fpay.application.port.in.SendSettlementsInfoUseCase;

import java.io.IOException;

@RestController
@RequestMapping("settlements")
@RequiredArgsConstructor
public class SettlementsController {
    private final PaymentSettlementsUseCase paymentSettlementsUseCase;
    private final SendSettlementsInfoUseCase sendSettlementsInfoUseCase;

    @GetMapping
    public boolean fetchSettlements() throws IOException {
        return paymentSettlementsUseCase.getPaymentSettlements();
    }

    @GetMapping("/produce")
    public boolean produceSettlements() throws Exception {
        return sendSettlementsInfoUseCase.send();
    }
}
