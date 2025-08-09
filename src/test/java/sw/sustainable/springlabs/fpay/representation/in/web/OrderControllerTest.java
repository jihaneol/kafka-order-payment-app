package sw.sustainable.springlabs.fpay.representation.in.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import sw.sustainable.springlabs.fpay.application.port.in.CreateNewOrderUseCase;
import sw.sustainable.springlabs.fpay.domain.order.Order;
import sw.sustainable.springlabs.fpay.representation.request.order.Orderer;
import sw.sustainable.springlabs.fpay.representation.request.order.PurchaseOrder;
import sw.sustainable.springlabs.fpay.representation.request.order.PurchaseOrderItem;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@WebMvcTest(controllers = OrderController.class)
public class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    CreateNewOrderUseCase createNewOrderUseCase;

    @Test
    @DisplayName("파라미터가 valid를 통과 못할때")
    public void newOrder() throws Exception {

        // given
        // 파라미터 객체 생성

        Orderer orderer = new Orderer("", "010-2222-222");
        List<PurchaseOrderItem> list = Arrays.asList(
                new PurchaseOrderItem(1, UUID.randomUUID(), "짜파게티", 1000, 2, 2000)
        );

        PurchaseOrder purchaseOrder = new PurchaseOrder(orderer, list);

        String requestJson = objectMapper.writeValueAsString(purchaseOrder);

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/order/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("ERROR"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.body.stackTraces").doesNotExist());

        String response = resultActions.andReturn().getResponse().getContentAsString();

        System.out.println(response);


    }

    @Test
    @DisplayName("새로운 주문이 접수가 완료되었을때")
    public void newOrderSuccess() throws Exception {
        //given
        Orderer orderer = new Orderer("", "010-2222-222");
        List<PurchaseOrderItem> list = Arrays.asList(
                new PurchaseOrderItem(1, UUID.randomUUID(), "짜파게티", 1000, 2, 2000)
        );

        PurchaseOrder purchaseOrder = new PurchaseOrder(orderer, list);

        String requestJson = objectMapper.writeValueAsString(purchaseOrder);

        Order order = purchaseOrder.toEntity();
        order.calculateTotalAmount();
        order.verifyHaveAtLeastOneItem();
        order.verifyDuplicateOrderItemId();

        BDDMockito.given(createNewOrderUseCase.createNewOrder(ArgumentMatchers.any()))
                .willReturn(order);

        mockMvc.perform(MockMvcRequestBuilders.post("/order/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.body.orderer.name").value("박의연"));


    }

    @Test
    @DisplayName("컨버터 테스트 하기")
    public void test() throws Exception {
         mockMvc.perform(MockMvcRequestBuilders.get("/order")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(result ->
                        {
                            var json = objectMapper.readTree(result.getResponse().getContentAsString());
                            assertThat(json.at("/status").asText()).isEqualTo("SUCCESS");
                            assertThat(json.at("/body").asText()).isEqualTo("OK");
                        }
                );
    }

}
