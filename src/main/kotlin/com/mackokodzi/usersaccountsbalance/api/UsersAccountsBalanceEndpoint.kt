package com.mackokodzi.usersaccountsbalance.api

import com.mackokodzi.usersaccountsbalance.domain.account.AccountId
import com.mackokodzi.usersaccountsbalance.domain.account.Price
import com.mackokodzi.usersaccountsbalance.domain.account.UserAccountBalanceProcessor
import com.mackokodzi.usersaccountsbalance.domain.user.UserId
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(value = ["/users"])
class UsersAccountsBalanceEndpoint(
    private val userAccountBalanceProcessor: UserAccountBalanceProcessor
) {

    @GetMapping(
        value = ["/{userId}/accounts/{accountId}/balance/USD"],
        produces = [APPLICATION_JSON_VALUE]
    )
    fun getBalance(
        @PathVariable("userId") userId: String, @PathVariable("accountId") accountId: String
    ): UserAccountBalanceApi =
        UserAccountBalanceApi(
            userAccountBalanceProcessor.process(UserId(userId), AccountId(accountId))
        )
}

data class UserAccountBalanceApi(
    val balance: Price
)