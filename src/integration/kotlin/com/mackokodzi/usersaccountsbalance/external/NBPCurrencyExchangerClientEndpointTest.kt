package com.mackokodzi.usersaccountsbalance.external

import com.mackokodzi.usersaccountsbalance.external.account.MongoUserAccountDocumentRepository
import com.mackokodzi.usersaccountsbalance.support.NbpCurrencyExchangerAbility
import org.springframework.beans.factory.annotation.Autowired

class NBPCurrencyExchangerClientEndpointTest(
    @Autowired private val mongoUserAccountDocumentRepository: MongoUserAccountDocumentRepository
) : NbpCurrencyExchangerAbility() {

    // TODO - tests for NBP currency client (2xx, 4xx, 5xx, SocketTimeoutExceptions)
}