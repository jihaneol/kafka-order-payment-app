package sw.sustainable.springlabs.fpay.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sw.sustainable.springlabs.fpay.application.port.in.PaymentSettlementsUseCase;
import sw.sustainable.springlabs.fpay.application.port.out.api.PaymentAPIs;
import sw.sustainable.springlabs.fpay.application.port.out.repository.SettlementsRepository;
import sw.sustainable.springlabs.fpay.domain.settlements.PaymentSettlements;
import sw.sustainable.springlabs.fpay.infrastructure.out.pg.toss.response.ResponsePaymentSettlements;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SettlementsService implements PaymentSettlementsUseCase {

    private final PaymentAPIs mockTossPayments;
    private final SettlementsRepository settlementsRepository;
    @Override
    public boolean getPaymentSettlements() throws IOException {

        List<ResponsePaymentSettlements> settlements = mockTossPayments.requestPaymentSettlement();

        List<PaymentSettlements> list= settlements.stream().map(ResponsePaymentSettlements::toEntity).toList();
        settlementsRepository.bulkInsert(list);

        return false;
    }
}
