package com.mackokodzi.usersaccountsbalance.mothers.external

import com.mackokodzi.usersaccountsbalance.domain.account.Price
import com.mackokodzi.usersaccountsbalance.external.account.UserAccountDocument
import org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric
import java.math.BigDecimal
import java.util.Currency

object UserAccountDocumentMother {
    fun build(
        userId: String = randomAlphanumeric(6),
        amount: BigDecimal = BigDecimal.TEN,
        currency: Currency = Currency.getInstance("PLN")
    ) =
        UserAccountDocument(
            userId = userId,
            balance = Price(amount = amount, currency = currency)
        )
}