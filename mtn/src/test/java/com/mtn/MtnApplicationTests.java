package com.mtn;

import com.mtn.dto.DeliveryNotification;
import com.mtn.dto.Payer;
import com.mtn.dto.RequestToPay;
import com.mtn.responses.AccountBalanceResponse;
import com.mtn.responses.AccountHolderResponse;
import com.mtn.responses.RequestToPayResponse;
import com.mtn.services.MTNServices;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class MtnApplicationTests {

    private MTNServices mtnServices;

    @Autowired
    public void setMtnServices(MTNServices mtnServices) {
        this.mtnServices = mtnServices;
    }

    @Test
    void contextLoads() {
    }

    @Test
    void testGetToken() {

        String response = MTNServices.getToken();
        assertThat(response).isNotNull();
    }

    @Test
    void testRequestToPay() {
        Payer payer = new Payer("MSISDN", "260359499393");
        RequestToPay request = new RequestToPay("12.0", "EUR", "260359499393", payer, "Payment Request", "Kindly settle within 72hrs");
        String response = mtnServices.requestToPay(request);
        assertThat(response).isEqualTo("202");
    }

    @Test
    void testRequestToPayStatus() {
        RequestToPayResponse response = mtnServices.requestToPayStatus("2g651s9-49b72bb2c4-4ba0-a00f-62d992");
        assertThat(response.getStatus()).isEqualTo("SUCCESSFUL");
    }

    @Test
    void testrequestToPayDelivery() {
        DeliveryNotification notification = new DeliveryNotification("Payment with 72hrs");
        String response = mtnServices.requestToPayDelivery(notification, "2g651s9-49b72bb2c4-4ba0-a00f-62d992");
        assertThat(response).isEqualTo("200");
    }

    @Test
    void testGetAccountBalance() {
        AccountBalanceResponse response = mtnServices.getAccountBalance();
        assertThat(response.getCurrency()).isEqualTo("EUR");
    }

    @Test
    void testGetAccountHolderInfo() {
        AccountHolderResponse response = mtnServices.getAccountHolderInfo("260359499393");
        assertThat(response.isResult()).isEqualTo(true);
    }

}
