package com.mackokodzi.usersaccountsbalance.external.currency

import com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.ktor.client.HttpClient
import io.ktor.client.engine.apache.Apache
import io.ktor.client.features.json.JacksonSerializer
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.request.accept
import io.ktor.client.request.get
import io.ktor.http.ContentType
import kotlinx.coroutines.runBlocking
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Repository

@Repository
class NBPCurrencyExchangerClient(
    private val nbpCurrencyExchangerHttpClient: HttpClient,
    @Value("\${client.nbp-currency-exchanger.url}") private val nbpUrl: String
) {
    fun getUsdExchangeRate() : NBPRatesResponse =
        runBlocking {
            nbpCurrencyExchangerHttpClient.get<NBPRatesResponse>("$nbpUrl$NBP_USD_RATE_URL") {
                accept(ContentType.Application.Json)
            }
        }

    companion object {
        const val NBP_USD_RATE_URL = "/api/exchangerates/rates/a/usd?format=json"
    }
}

data class NBPRatesResponse(
    val rates: List<RatesResponse>
)

data class RatesResponse(
    val mid: Double
)

@Configuration
class NPBCurrencyExchangerClientConfig {
    @Bean
    fun nbpCurrencyExchangerHttpClient() = HttpClient(Apache) {
        install(JsonFeature) {
            serializer = JacksonSerializer(
                jacksonObjectMapper().disable(FAIL_ON_UNKNOWN_PROPERTIES)
            )
        }
    }
}