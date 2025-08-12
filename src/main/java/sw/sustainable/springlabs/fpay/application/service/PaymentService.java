package sw.sustainable.springlabs.fpay.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sw.sustainable.springlabs.fpay.application.port.in.PaymentFullFillUseCase;
import sw.sustainable.springlabs.fpay.application.port.out.api.PaymentAPIs;
import sw.sustainable.springlabs.fpay.application.port.out.repository.OrderRepository;
import sw.sustainable.springlabs.fpay.application.port.out.repository.PaymentLedgerRepository;
import sw.sustainable.springlabs.fpay.application.port.out.repository.TransactionTypeRepository;
import sw.sustainable.springlabs.fpay.representation.request.payment.PaymentApproved;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class PaymentService implements PaymentFullFillUseCase {
    private final PaymentAPIs tossPayments;
    private final OrderRepository orderRepository;
    private final PaymentLedgerRepository paymentLedgerRepository;
    private TransactionTypeRepository transactionTypeRepository;

    @Override
    public String paymentApproved(PaymentApproved paymentApproved) throws IOException {
        return "";
    }
}
