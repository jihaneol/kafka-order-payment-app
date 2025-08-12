package sw.sustainable.springlabs.fpay.infrastructure.out.pg.toss.mock;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import sw.sustainable.springlabs.fpay.infrastructure.out.pg.toss.response.ResponsePaymentApproved;
import sw.sustainable.springlabs.fpay.infrastructure.out.pg.toss.response.ResponsePaymentSettlements;
import sw.sustainable.springlabs.fpay.representation.request.payment.PaymentApproved;

import java.util.List;

public interface MockTossPaymentAPIs {
    @GET("settlements")
    Call<List<ResponsePaymentSettlements>> paymentSettlements();
}
