package com.mackokodzi.usersaccountsbalance.api

import com.mackokodzi.usersaccountsbalance.domain.account.AccountId
import org.springframework.http.HttpStatus.CONFLICT
import org.springframework.http.HttpStatus.NOT_FOUND
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import java.util.Currency
import javax.servlet.http.HttpServletRequest

@ControllerAdvice
class ExceptionHandler {

    @ExceptionHandler(value = [UserAccountNotFoundException::class])
    fun handleUserAccountNotFound(req: HttpServletRequest, e: UserAccountNotFoundException) =
        ResponseEntity
            .status(NOT_FOUND)
            .body(ErrorMessage(e.message!!))

    @ExceptionHandler(value = [IllegalAccountForGivenUserException::class])
    fun handleIllegalAccountForGivenUser(req: HttpServletRequest, e: IllegalAccountForGivenUserException) =
        ResponseEntity
            .status(CONFLICT)
            .body(ErrorMessage(e.message!!))

    @ExceptionHandler(value = [UnsupportedExchangeCurrencyException::class])
    fun handleIllegalAccountForGivenUser(req: HttpServletRequest, e: UnsupportedExchangeCurrencyException) =
        ResponseEntity
            .status(CONFLICT)
            .body(ErrorMessage(e.message!!))
}

data class ErrorMessage(val message: String)

class UserAccountNotFoundException(accountId: AccountId) : RuntimeException("Account ${accountId.value} not found")
class IllegalAccountForGivenUserException(accountId: AccountId) : RuntimeException("Illegal account ${accountId.value} found")
class UnsupportedExchangeCurrencyException(originCurrency: Currency, targetCurrency: Currency) : RuntimeException("Unsupported exchange from $originCurrency to $targetCurrency")