package com.mackokodzi.usersaccountsbalance

import com.fasterxml.jackson.databind.ObjectMapper
import com.xebialabs.restito.server.StubServer
import io.restassured.RestAssured
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration

@ContextConfiguration
@SpringBootTest(
	classes = [UsersAccountsBalanceApplication::class],
	webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT
)
@ActiveProfiles("integration")
abstract class BaseIntegrationTests {
	val objectMapper: ObjectMapper = ObjectMapper()
	lateinit var server: StubServer

	@BeforeEach
	fun beforeEach() {
		server = StubServer(7777).run()
		RestAssured.port = server.port
	}

	@AfterEach
	fun afterEach() {
		server.stop()
	}
}