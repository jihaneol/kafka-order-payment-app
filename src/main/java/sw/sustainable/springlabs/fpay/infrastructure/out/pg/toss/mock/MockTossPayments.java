package sw.sustainable.springlabs.fpay.infrastructure.out.pg.toss.mock;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import retrofit2.Response;
import sw.sustainable.springlabs.fpay.application.port.out.api.PaymentAPIs;
import sw.sustainable.springlabs.fpay.infrastructure.out.pg.toss.response.ResponsePaymentApproved;
import sw.sustainable.springlabs.fpay.infrastructure.out.pg.toss.response.ResponsePaymentCancel;
import sw.sustainable.springlabs.fpay.infrastructure.out.pg.toss.response.ResponsePaymentSettlements;
import sw.sustainable.springlabs.fpay.representation.request.payment.PaymentApproved;
import sw.sustainable.springlabs.fpay.representation.request.payment.PaymentCancel;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class MockTossPayments implements PaymentAPIs {
    private final MockTossPaymentAPIs mockTossPaymentAPI;

    @Override
    public ResponsePaymentApproved requestPaymentApprove(PaymentApproved requestMessage) throws IOException {
        return null;
    }

    @Override
    public boolean isPaymentApproved(String status) {
        return false;
    }

    @Override
    public ResponsePaymentCancel requestPaymentCancel(String paymentKey, PaymentCancel cancelMessage) throws IOException {
        return null;
    }

    @Override
    public List<ResponsePaymentSettlements> requestPaymentSettlement() throws IOException {

        Response<List<ResponsePaymentSettlements>> response= mockTossPaymentAPI.paymentSettlements().execute();
        if(response.isSuccessful() && response.body()!=null && !response.body().isEmpty()) {
            return response.body();
        }
        throw new IOException(response.message());
    }
}
