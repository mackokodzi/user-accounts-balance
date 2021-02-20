package com.mackokodzi.usersaccountsbalance.external.currency

import com.mackokodzi.usersaccountsbalance.domain.account.Price
import com.mackokodzi.usersaccountsbalance.domain.currency.NBPCurrencyRateFetcher
import com.mackokodzi.usersaccountsbalance.domain.currency.USD_CURRENCY
import org.springframework.stereotype.Repository

@Repository
class NBPCurrencyRateRepository(
    private val npbCurrencyExchangerClient: NBPCurrencyExchangerClient
) : NBPCurrencyRateFetcher {
    override fun getUSDCurrencyRate(): Price =
        npbCurrencyExchangerClient.getUsdExchangeRate().toDomain()

    private fun NBPRatesResponse.toDomain() =
        Price(
            amount = rates.first().mid.toBigDecimal(), //TODO: list may be empty
            currency = USD_CURRENCY
        )
}