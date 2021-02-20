package com.mackokodzi.usersaccountsbalance.domain.account

import com.mackokodzi.usersaccountsbalance.domain.user.UserId
import java.math.BigDecimal
import java.util.Currency

data class UserAccount(
    val id: AccountId,
    val userId: UserId,
    val balance: Price
)

data class AccountId(val value: String)

data class Price(
    val amount: BigDecimal,
    val currency: Currency
)