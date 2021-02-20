package com.mackokodzi.usersaccountsbalance.domain.account

interface UserAccountFetcher {
    fun getOrNull(accountId: AccountId) : UserAccount?
}