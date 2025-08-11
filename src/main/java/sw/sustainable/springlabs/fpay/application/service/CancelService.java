package sw.sustainable.springlabs.fpay.application.service;

import org.springframework.stereotype.Service;
import sw.sustainable.springlabs.fpay.application.port.in.PaymentCancelUseCase;
import sw.sustainable.springlabs.fpay.representation.request.order.CancelOrder;

import java.io.IOException;

@Service
public class CancelService implements PaymentCancelUseCase {
    @Override
    public boolean paymentCancel(CancelOrder cancelRequestMessage) throws IOException {
        return false;
    }
}
