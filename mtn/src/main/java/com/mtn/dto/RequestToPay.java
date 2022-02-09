package com.mtn.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestToPay {

    private String amount;
    private String currency;
    private String externalId;
    private Payer payer;
    private String payerMessage;
    private String payeeNote;

}
