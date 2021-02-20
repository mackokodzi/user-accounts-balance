package com.mackokodzi.usersaccountsbalance.external.account

import com.mackokodzi.usersaccountsbalance.domain.account.AccountId
import com.mackokodzi.usersaccountsbalance.domain.account.Price
import com.mackokodzi.usersaccountsbalance.domain.account.UserAccount
import com.mackokodzi.usersaccountsbalance.domain.account.UserAccountFetcher
import com.mackokodzi.usersaccountsbalance.domain.user.UserId
import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.math.BigDecimal
import java.util.Currency
import javax.annotation.PostConstruct

@Repository
class UserAccountRepository(
    private val mongoRepository: MongoUserAccountDocumentRepository
) : UserAccountFetcher {
    override fun getOrNull(accountId: AccountId) : UserAccount? =
        mongoRepository.findById(accountId.value)
            .map { it.toDomain() }
            .orElse(null)

    private fun UserAccountDocument.toDomain() =
        UserAccount(
            id = AccountId(id),
            userId = UserId(userId),
            balance = balance
        )

    /** Method used just for tests for populating mongo **/
    @PostConstruct
    fun onInit() {
        mongoRepository.save(buildUserAccountDocument(
            userId = "firstUser", accountId = "firstAccount", amount = BigDecimal.valueOf(10.00)))
        mongoRepository.save(buildUserAccountDocument(
            userId = "secondUser", accountId = "secondAccount", amount = BigDecimal.valueOf(20.00)))
    }

    companion object {
        fun buildUserAccountDocument(userId: String, accountId: String, amount : BigDecimal) =
            UserAccountDocument(
                id = accountId,
                userId = userId,
                balance = Price(amount = amount, currency = Currency.getInstance("PLN"))
            )
    }
}

interface MongoUserAccountDocumentRepository : CrudRepository<UserAccountDocument, String>

data class UserAccountDocument(
    @Id val id: String = ObjectId.get().toString(),
    val userId: String,
    val balance: Price
)