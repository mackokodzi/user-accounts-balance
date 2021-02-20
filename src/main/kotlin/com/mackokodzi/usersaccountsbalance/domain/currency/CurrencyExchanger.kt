package com.mackokodzi.usersaccountsbalance.domain.currency

import com.mackokodzi.usersaccountsbalance.api.UnsupportedExchangeCurrencyException
import com.mackokodzi.usersaccountsbalance.domain.account.Price
import org.springframework.stereotype.Component
import java.math.RoundingMode
import java.util.Currency

@Component
class CurrencyExchanger(private val nbpCurrencyRateFetcher: NBPCurrencyRateFetcher) {
    fun exchange(originAmount: Price, targetCurrency: Currency) : Price {
        validate(originAmount.currency, targetCurrency)

        return nbpCurrencyRateFetcher.getUSDCurrencyRate()
            .let {
                Price(
                    amount = originAmount.amount.divide(it.amount, 2, RoundingMode.UP),
                    currency = USD_CURRENCY
                )
            }
    }

    private fun validate(originCurrency: Currency, targetCurrency: Currency) {
        if (originCurrency != PLN_CURRENCY || targetCurrency != USD_CURRENCY) {
            throw UnsupportedExchangeCurrencyException(originCurrency, targetCurrency)
        }
    }
}

val PLN_CURRENCY: Currency = Currency.getInstance("PLN")
val USD_CURRENCY: Currency = Currency.getInstance("USD")