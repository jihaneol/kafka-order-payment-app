package sw.sustainable.springlabs.fpay.application.service;

import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sw.sustainable.springlabs.fpay.application.port.in.PaymentFullFillUseCase;
import sw.sustainable.springlabs.fpay.application.port.out.api.PaymentAPIs;
import sw.sustainable.springlabs.fpay.application.port.out.repository.OrderRepository;
import sw.sustainable.springlabs.fpay.application.port.out.repository.PaymentLedgerRepository;
import sw.sustainable.springlabs.fpay.application.port.out.repository.TransactionTypeRepository;
import sw.sustainable.springlabs.fpay.domain.order.Order;
import sw.sustainable.springlabs.fpay.domain.order.OrderStatus;
import sw.sustainable.springlabs.fpay.domain.payment.PaymentMethod;
import sw.sustainable.springlabs.fpay.domain.payment.TransactionType;
import sw.sustainable.springlabs.fpay.infrastructure.out.pg.toss.response.ResponsePaymentApproved;
import sw.sustainable.springlabs.fpay.representation.request.payment.PaymentApproved;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentService implements PaymentFullFillUseCase {
    private final PaymentAPIs tossPayments;
    private final OrderRepository orderRepository;
    private final PaymentLedgerRepository paymentLedgerRepository;
    private final Set<TransactionTypeRepository> transactionTypeRepositorySet;
    private final Map<String, TransactionTypeRepository> transactionTypeRepositories = new HashMap<>();

    @PostConstruct
    public void init() {
        for(TransactionTypeRepository transactionTypeRepository : transactionTypeRepositorySet) {
            String paymentMethodType = transactionTypeRepository.getClass().getSimpleName().split("TransactionTypeRepository")[0].toLowerCase();
            transactionTypeRepositories.put(paymentMethodType, transactionTypeRepository);
        }
    }


    @Override
    @Transactional
    public String paymentApproved(PaymentApproved paymentApproved) throws IOException {
        verityOrderIsCompleted(UUID.fromString(paymentApproved.getOrderId()));

        ResponsePaymentApproved response = tossPayments.requestPaymentApprove(paymentApproved);

        if(tossPayments.isPaymentApproved(response.getStatus())){
            Order completedOrder = orderRepository.findById(UUID.fromString(response.getOrderId()));
            completedOrder.orderPaymentFullFill(response.getPaymentKey());

            paymentLedgerRepository.save(response.toPaymentTransactionEntity());
            PaymentMethod method = PaymentMethod.fromMethodName(response.getMethod());

            TransactionTypeRepository transactionTypeRepository = transactionTypeRepositories.get(method.toString().toLowerCase());
            transactionTypeRepository.save(TransactionType.convertToTransactionType(response));

            return "success";
        }

        return "fail";
    }

    private void verityOrderIsCompleted(UUID orderId) {
       OrderStatus orderStatus = orderRepository.findById(UUID.fromString(orderId.toString())).getStatus();
        if(!OrderStatus.ORDER_COMPLETED.equals(orderStatus)){
            throw new IllegalArgumentException("Order is not completed");
        }
    }
}
