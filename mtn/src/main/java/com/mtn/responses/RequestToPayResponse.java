package com.mtn.responses;

import com.mtn.dto.Payer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestToPayResponse {


    private String financialTransactionId;
    private String externalId;
    private String amount;
    private String currency;
    private Payer payer;
    private String payerMessage;
    private String payeeNote;
    private String status;

}
