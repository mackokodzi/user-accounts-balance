package com.mackokodzi.usersaccountsbalance

import com.mackokodzi.usersaccountsbalance.external.account.MongoUserAccountDocumentRepository
import com.mackokodzi.usersaccountsbalance.mothers.external.NbpRatesResponseMother
import com.mackokodzi.usersaccountsbalance.mothers.external.UserAccountDocumentMother
import com.mackokodzi.usersaccountsbalance.support.NbpCurrencyExchangerAbility
import io.restassured.module.kotlin.extensions.Given
import io.restassured.module.kotlin.extensions.Then
import io.restassured.module.kotlin.extensions.When
import org.hamcrest.Matchers.equalTo
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR
import org.springframework.http.HttpStatus.NOT_FOUND
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import java.util.Currency

class UsersAccountsBalanceEndpointTest(
    @Autowired private val mongoUserAccountDocumentRepository: MongoUserAccountDocumentRepository
) : NbpCurrencyExchangerAbility() {

    @Test
    fun `should get user account balance expressed in USD`() {
        // given
        val userAccountDocument = UserAccountDocumentMother.build()
        mongoUserAccountDocumentRepository.save(userAccountDocument)
        val nbpRatesResponse = NbpRatesResponseMother.build()

        // and
        stubGetUsdExchangeRate(nbpRatesResponse)

        // when
        Given {
            accept(APPLICATION_JSON_VALUE)
        } When {
            get("http://localhost:8080/users/${userAccountDocument.userId}/accounts/${userAccountDocument.id}/balance/USD")
        } Then {
            // then
            statusCode(HttpStatus.OK.value())
                .body("balance.currency", equalTo("USD"))
                .body("balance.amount", equalTo(2.7f))
        }

        //and verify call to nbp currency exchanger
        verifyGetUsdExchangeRate()
    }

    @Test
    fun `should return 409 when user account balance is not in PLN`() {
        // given
        val userAccountDocument = UserAccountDocumentMother.build(
            currency = Currency.getInstance("CZK")
        )
        mongoUserAccountDocumentRepository.save(userAccountDocument)
        val nbpRatesResponse = NbpRatesResponseMother.build()

        // and
        stubGetUsdExchangeRate(nbpRatesResponse)

        // when
        Given {
            accept(APPLICATION_JSON_VALUE)
        } When {
            get("http://localhost:8080/users/${userAccountDocument.userId}/accounts/${userAccountDocument.id}/balance/USD")
        } Then {
            // then
            statusCode(HttpStatus.CONFLICT.value())
        }
    }

    @Test
    fun `should return 500 when nbp rates exchanger responds with error status code`() {
        // given
        val userAccountDocument = UserAccountDocumentMother.build()
        mongoUserAccountDocumentRepository.save(userAccountDocument)

        // and
        stubGetUsdExchangeRateFailed(INTERNAL_SERVER_ERROR.value())

        // when
        Given {
            accept(APPLICATION_JSON_VALUE)
        } When {
            get("http://localhost:8080/users/${userAccountDocument.userId}/accounts/${userAccountDocument.id}/balance/USD")
        } Then {
            // then
            statusCode(INTERNAL_SERVER_ERROR.value())
        }
    }

    @Test
    fun `should return 404 if user account not found`() {
        // when
        Given {
            accept(APPLICATION_JSON_VALUE)
        } When {
            get("http://localhost:8080/users/1234/accounts/7890/balance/USD")
        } Then {
            // then
            statusCode(NOT_FOUND.value())
        }
    }
}