package com.mackokodzi.usersaccountsbalance.domain.account

import com.mackokodzi.usersaccountsbalance.api.IllegalAccountForGivenUserException
import com.mackokodzi.usersaccountsbalance.api.UserAccountNotFoundException
import com.mackokodzi.usersaccountsbalance.domain.currency.CurrencyExchanger
import com.mackokodzi.usersaccountsbalance.domain.currency.USD_CURRENCY
import com.mackokodzi.usersaccountsbalance.domain.user.UserId
import org.springframework.stereotype.Component

@Component
class UserAccountBalanceProcessor(
    private val userAccountFetcher: UserAccountFetcher,
    private val currencyExchanger: CurrencyExchanger
) {
    fun process(userId: UserId, accountId: AccountId): Price =
        userAccountFetcher.getOrNull(accountId)
            ?.also { it.validate(userId) }
            ?.let { currencyExchanger.exchange(it.balance, USD_CURRENCY) }
            ?: throw UserAccountNotFoundException(accountId)

    private fun UserAccount.validate(userIdFromRequest: UserId) {
        if (this.userId.value != userIdFromRequest.value) {
            throw IllegalAccountForGivenUserException(this.id)
        }
    }
}

