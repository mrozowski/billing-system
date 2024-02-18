package com.mrozowski.softwarepartnertask.adapter.incoming

import com.fasterxml.jackson.databind.ObjectMapper
import com.mrozowski.softwarepartnertask.SpringIntegrationSpecBase
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.web.util.UriComponentsBuilder

class BillingControllerIntegrationSpec extends SpringIntegrationSpecBase {

  private static final String PARENT_BILLING_ENDPOINT = "/v1/billing/parent"

  @Autowired
  private JdbcTemplate jdbcTemplate
  private TestRestTemplate restTemplate = new TestRestTemplate()
  private ObjectMapper mapper = new ObjectMapper()

  def "should return monthly billing for a given parent"() {
    given:
    def expectedJsonResponse = getClass().getResourceAsStream("/json/parentBillingResponse.json").text
    def sqlScript = getClass().getResourceAsStream("/sql/testData.sql").text
    jdbcTemplate.execute(sqlScript)

    def uri = UriComponentsBuilder.newInstance()
        .scheme("http")
        .host("localhost")
        .port(port)
        .path(PARENT_BILLING_ENDPOINT)
        .queryParam("id", "1")
        .queryParam("year", "2024")
        .queryParam("month", "2")
        .build()
        .toUri()

    when:
    def json = restTemplate.getForEntity(uri, String.class)


    then:
    def expectedJson = mapper.readTree(expectedJsonResponse)
    def actualJson = mapper.readTree(json.getBody())
    actualJson == expectedJson
  }
}
