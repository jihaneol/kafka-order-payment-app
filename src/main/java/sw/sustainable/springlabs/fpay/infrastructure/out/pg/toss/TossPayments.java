package sw.sustainable.springlabs.fpay.infrastructure.out.pg.toss;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import retrofit2.Response;
import sw.sustainable.springlabs.fpay.application.port.out.api.PaymentAPIs;
import sw.sustainable.springlabs.fpay.infrastructure.out.pg.toss.response.ResponsePaymentApproved;
import sw.sustainable.springlabs.fpay.infrastructure.out.pg.toss.response.ResponsePaymentCancel;
import sw.sustainable.springlabs.fpay.infrastructure.out.pg.toss.response.ResponsePaymentSettlements;
import sw.sustainable.springlabs.fpay.representation.request.payment.PaymentApproved;
import sw.sustainable.springlabs.fpay.representation.request.payment.PaymentCancel;
import sw.sustainable.springlabs.fpay.representation.request.payment.PaymentSettlement;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

@Component
@RequiredArgsConstructor
public class TossPayments implements PaymentAPIs {
    private final TossPaymentAPIs tossClient;

    public ResponsePaymentApproved requestPaymentApprove(PaymentApproved paymentInfo) throws IOException {
        Response<ResponsePaymentApproved> response = tossClient.paymentFullfill(paymentInfo).execute();
        if(response.isSuccessful()) {
            return response.body();
        }
        throw new IOException(response.message());
    }

    @Override
    public boolean isPaymentApproved(String status) {
        if("DONE".equals(status)) {
            return true;
        }
        return false;
    }

    @Override
    public ResponsePaymentCancel requestPaymentCancel(String paymentKey, PaymentCancel cancelMessage) throws IOException {
        return null;
    }

    @Override
    public List<ResponsePaymentSettlements> requestPaymentSettlement() throws IOException {
        String startDate = LocalDate.now(ZoneId.of("Asia/Seoul")).minusDays(3).toString();
        String endDate = LocalDate.now(ZoneId.of("Asia/Seoul")).toString();
        int page = 1;
        int size = 5000;
        Response<List<ResponsePaymentSettlements>> response = tossClient.paymentSettlements(startDate, endDate, page, size).execute();
        if(response.isSuccessful() && response.body() != null &&  !response.body().isEmpty()) {
            return response.body();
        }
        throw new IOException(response.message());
    }
}
