package sw.sustainable.springlabs.fpay.application.port.in;

import java.io.IOException;

public interface PaymentSettlementsUseCase {
    boolean getPaymentSettlements() throws IOException;
}
