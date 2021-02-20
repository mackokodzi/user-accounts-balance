package com.mackokodzi.usersaccountsbalance.domain.currency

import com.mackokodzi.usersaccountsbalance.domain.account.Price

interface NBPCurrencyRateFetcher {
    fun getUSDCurrencyRate() : Price
}
