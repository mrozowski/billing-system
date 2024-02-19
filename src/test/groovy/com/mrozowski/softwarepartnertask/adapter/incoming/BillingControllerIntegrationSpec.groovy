package com.mrozowski.softwarepartnertask.adapter.incoming

import com.fasterxml.jackson.databind.ObjectMapper
import com.mrozowski.softwarepartnertask.SpringIntegrationSpecBase
import org.spockframework.spring.EnableSharedInjection
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.web.util.UriComponentsBuilder
import spock.lang.Shared

@EnableSharedInjection
class BillingControllerIntegrationSpec extends SpringIntegrationSpecBase {

  private static final String PARENT_BILLING_ENDPOINT = "/v1/billing/parent"
  private static final String SCHOOL_BILLING_ENDPOINT = "/v1/billing/school"

  @Shared
  @Autowired
  private JdbcTemplate jdbcTemplate
  private TestRestTemplate restTemplate = new TestRestTemplate()
  private ObjectMapper mapper = new ObjectMapper()

  def setupSpec() {
    def sqlScript = getClass().getResourceAsStream("/sql/testData.sql").text
    jdbcTemplate.execute(sqlScript)
  }

  def 'should return monthly billing for a given parent'() {
    given:
    def expectedJsonResponse = getClass().getResourceAsStream("/json/parentBillingResponse.json").text
    def uri = createUriFor(PARENT_BILLING_ENDPOINT)

    when:
    def json = restTemplate.getForEntity(uri, String.class)

    then:
    def expectedJson = mapper.readTree(expectedJsonResponse)
    def actualJson = mapper.readTree(json.getBody())
    actualJson == expectedJson
  }

  def 'should return monthly billing for a given school'() {
    given:
    def expectedJsonResponse = getClass().getResourceAsStream("/json/schoolBillingResponse.json").text
    def uri = createUriFor(SCHOOL_BILLING_ENDPOINT)

    when:
    def json = restTemplate.getForEntity(uri, String.class)

    then:
    def expectedJson = mapper.readTree(expectedJsonResponse)
    def actualJson = mapper.readTree(json.getBody())
    actualJson == expectedJson
  }

  private URI createUriFor(String endpoint) {
    UriComponentsBuilder.newInstance()
        .scheme("http")
        .host("localhost")
        .port(port)
        .path(endpoint)
        .queryParam("id", "1")
        .queryParam("year", "2024")
        .queryParam("month", "2")
        .build()
        .toUri()
  }
}
