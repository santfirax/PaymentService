package com.example.paymentservice;

import com.github.tomakehurst.wiremock.client.WireMock;
import org.assertj.core.api.BDDAssertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import static reactor.core.publisher.Mono.when;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureWireMock(port = 8082)
class PaymentserviceApplicationTests {

    private static final String RESPONSE = "[\n" +
            "{\n" +
            "\t\"company\":\"Une\",\n" +
            "\t\"amount\":50000\n" +
            "},\n" +
            "{\n" +
            "\t\"company\":\"Claro\",\n" +
            "\t\"amount\":80000\n" +
            "}\n" +
            "]";

    @Test
    public void shouldGetAllBills() {

        WireMock.stubFor(WireMock.get(WireMock.urlEqualTo("/bills"))
        .willReturn(WireMock.aResponse().withBody(RESPONSE).withStatus(200)));
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> entity = restTemplate.getForEntity("http://localhost:8082/bills", String.class);
        BDDAssertions.then(entity.getStatusCodeValue()).isEqualTo(200);
        BDDAssertions.then(entity.getBody()).isEqualTo(RESPONSE);

    }

}
