package com.mtn.services;


import com.google.gson.Gson;
import com.mtn.dto.DeliveryNotification;
import com.mtn.dto.RequestToPay;
import com.mtn.responses.AccountBalanceResponse;
import com.mtn.responses.AccountHolderResponse;
import com.mtn.responses.RequestToPayResponse;
import com.mtn.responses.TokenResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import static com.mtn.constants.Constants.*;

@Service
@Slf4j
public class MTNServices {

    private static WebClientService webclientService;

    public static String getToken() {
        TokenResponse res = webclientService.requestWithEndpoint(domainUrl + tokenUrl)
                .post()
                .header("Ocp-Apim-Subscription-Key", "xxxxxxxxxxxxxxxxxxxxxxxxx")
                .header("Authorization", "Basic xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx")
                .retrieve()
                .bodyToMono(TokenResponse.class)
                .block();

        String result = "Bearer " + res.getAccess_token();
        log.info("Result: " + result);

        return result;
    }

    @Autowired
    public void setWebclientService(WebClientService webclientService) {
        this.webclientService = webclientService;
    }

    public String requestToPay(RequestToPay request) {
        String token = getToken();
        HttpStatus res = webclientService.requestWithEndpoint(domainUrl + paymentRequestUrl)
                .post()
                .header("Ocp-Apim-Subscription-Key", "xxxxxxxxxxxxxxxxxxxxxxxxx")
                .header("X-Reference-Id", "6f2641f9-41b7-2ba0-a00f-69bb2c6d9572")
                .header("X-Target-Environment", "sandbox")
                .header("Authorization", token)
                .body(Mono.just(request), RequestToPay.class)
                .retrieve()
                .toBodilessEntity()
                .map(response -> response.getStatusCode())
                .block();

        Gson gson = new Gson();
        String result = gson.toJson(res.value());
        log.info("Result: " + result);

        return result;
    }

    public RequestToPayResponse requestToPayStatus(String referenceID) {
        String token = getToken();
        RequestToPayResponse res = webclientService.requestWithEndpoint(domainUrl + paymentRequestUrl + "/" + referenceID)
                .get()
                .header("Ocp-Apim-Subscription-Key", "xxxxxxxxxxxxxxxxxxxxxxxxx")
                .header("X-Target-Environment", "sandbox")
                .header("Authorization", token)
                .retrieve()
                .bodyToMono(RequestToPayResponse.class)
                .block();
        Gson gson = new Gson();
        String result = gson.toJson(res);
        log.info("Result: " + result);

        return res;
    }

    public String requestToPayDelivery(DeliveryNotification request, String referenceID) {
        String token = getToken();
        HttpStatus res = webclientService.requestWithEndpoint(domainUrl + paymentRequestUrl + referenceID + "/deliverynotification")
                .post()
                .header("Ocp-Apim-Subscription-Key", "xxxxxxxxxxxxxxxxxxxxxxxxx")
                .header("notificationMessage", "Zain Euro")
                .header("X-Target-Environment", "sandbox")
                .header("Authorization", token)
                .body(Mono.just(request), DeliveryNotification.class)
                .retrieve()
                .toBodilessEntity()
                .map(response -> response.getStatusCode())
                .block();

        Gson gson = new Gson();
        String result = gson.toJson(res.value());
        log.info("Result: " + result);

        return result;
    }

    public AccountBalanceResponse getAccountBalance() {
        String token = getToken();
        AccountBalanceResponse res = webclientService.requestWithEndpoint(domainUrl + accountBalanceUrl)
                .get()
                .header("Ocp-Apim-Subscription-Key", "xxxxxxxxxxxxxxxxxxxxxxxxx")
                .header("X-Target-Environment", "sandbox")
                .header("Authorization", token)
                .retrieve()
                .bodyToMono(AccountBalanceResponse.class)
                .block();
        Gson gson = new Gson();
        String result = gson.toJson(res);
        log.info("Result: " + result);

        return res;
    }

    public AccountHolderResponse getAccountHolderInfo(String msisdn) {
        String token = getToken();
        AccountHolderResponse res = webclientService.requestWithEndpoint(domainUrl + accountHolderUrl + msisdn + "/active")
                .get()
                .header("Ocp-Apim-Subscription-Key", "xxxxxxxxxxxxxxxxxxxxxxxxx")
                .header("X-Target-Environment", "sandbox")
                .header("Authorization", token)
                .retrieve()
                .bodyToMono(AccountHolderResponse.class)
                .block();
        Gson gson = new Gson();
        String result = gson.toJson(res);
        log.info("Result: " + result);

        return res;
    }

}
