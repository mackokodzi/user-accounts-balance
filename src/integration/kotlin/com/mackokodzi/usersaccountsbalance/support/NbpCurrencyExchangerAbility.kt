package com.mackokodzi.usersaccountsbalance.support

import com.mackokodzi.usersaccountsbalance.BaseIntegrationTests
import com.mackokodzi.usersaccountsbalance.external.currency.NBPRatesResponse
import com.xebialabs.restito.builder.stub.StubHttp
import com.xebialabs.restito.builder.stub.StubHttp.whenHttp
import com.xebialabs.restito.builder.verify.VerifyHttp
import com.xebialabs.restito.builder.verify.VerifyHttp.verifyHttp
import com.xebialabs.restito.semantics.Action
import com.xebialabs.restito.semantics.Action.contentType
import com.xebialabs.restito.semantics.Action.status
import com.xebialabs.restito.semantics.Action.stringContent
import com.xebialabs.restito.semantics.Condition
import com.xebialabs.restito.semantics.Condition.get
import com.xebialabs.restito.semantics.Condition.method
import com.xebialabs.restito.semantics.Condition.startsWithUri
import org.glassfish.grizzly.http.Method
import org.glassfish.grizzly.http.Method.GET
import org.glassfish.grizzly.http.util.HttpStatus
import org.glassfish.grizzly.http.util.HttpStatus.getHttpStatus
import org.springframework.http.MediaType
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE

class NbpCurrencyExchangerAbility : BaseIntegrationTests() {

    fun stubGetUsdExchangeRate(nbpRatesResponse: NBPRatesResponse) {
        whenHttp(server)
            .match(
                method(GET),
                startsWithUri("/api/exchangerates/rates/a/usd")
            )
            .then(
                status(getHttpStatus(200)),
                contentType(APPLICATION_JSON_VALUE),
                stringContent(objectMapper.writeValueAsString(nbpRatesResponse))
            )
    }

    fun stubGetUsdExchangeRateFailed(httpStatusCode: Int = 500) {
        whenHttp(server)
            .match(
                method(GET),
                startsWithUri("/api/exchangerates/rates/a/usd")
            )
            .then(
                status(getHttpStatus(httpStatusCode))
            )
    }

    fun verifyGetUsdExchangeRate(times: Int = 1) {
        verifyHttp(server).times(times,
            get("/api/exchangerates/rates/a/usd")
        )
    }
}