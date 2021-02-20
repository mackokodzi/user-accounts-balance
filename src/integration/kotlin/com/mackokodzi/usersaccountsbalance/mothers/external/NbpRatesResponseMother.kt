package com.mackokodzi.usersaccountsbalance.mothers.external

import com.mackokodzi.usersaccountsbalance.external.currency.NBPRatesResponse
import com.mackokodzi.usersaccountsbalance.external.currency.RatesResponse

object NbpRatesResponseMother {
    fun build(
        rates: List<RatesResponse> = listOf(RatesResponse(3.7152))
    ) =
        NBPRatesResponse(
            rates = rates
        )
}