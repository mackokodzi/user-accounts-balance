package com.mackokodzi.usersaccountsbalance.domain.user

data class User(
    val id: UserId,
    val name: UserName
)

data class UserId(val value: String)
data class UserName(val value: String)