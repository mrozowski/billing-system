package com.mrozowski.softwarepartnertask

import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.spock.Testcontainers
import spock.lang.Shared
import spock.lang.Specification

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT

@Testcontainers
@SpringBootTest(webEnvironment = RANDOM_PORT)
@ActiveProfiles("test")
class SpringIntegrationSpecBase extends Specification {

  @LocalServerPort
  int port

  @Shared
  static PostgreSQLContainer postgresqlContainer = new PostgreSQLContainer("postgres:12")

  @DynamicPropertySource
  private static void overrideProperties(DynamicPropertyRegistry registry) {
    postgresqlContainer.start()
    registry.add("spring.datasource.url", postgresqlContainer::getJdbcUrl)
    registry.add("spring.datasource.username", postgresqlContainer::getUsername)
    registry.add("spring.datasource.password", postgresqlContainer::getPassword)
  }
}
