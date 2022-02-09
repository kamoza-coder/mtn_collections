package com.mtn.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountBalanceResponse {

    private String availableBalance;
    private String currency;

}
