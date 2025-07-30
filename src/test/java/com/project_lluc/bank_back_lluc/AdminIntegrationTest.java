package com.project_lluc.bank_back_lluc;

import com.project_lluc.bank_back_lluc.model.accounts.Checking;
import com.project_lluc.bank_back_lluc.model.shared.Money;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AdminIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void createAndGetBalance() {
        // 1) Cliente admin
        TestRestTemplate admin = restTemplate.withBasicAuth("admin", "admin");
        String base = "http://localhost:" + port + "/api/admin";

        // --- 2) Crear una cuenta Checking
        Checking chk = new Checking();
        chk.setBalance(new Money(new BigDecimal("300.00")));
        chk.setSecretKey("mySecret123");
        // setea aquí tus owners si son obligatorios...

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Checking> req = new HttpEntity<>(chk, headers);

        ResponseEntity<Checking> createResponse =
                admin.postForEntity(base + "/accounts/checking", req, Checking.class);

        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        Checking created = createResponse.getBody();
        assertThat(created).isNotNull();
        Long id = created.getId();
        assertThat(id).isNotNull();

        // Capturamos el balance que realmente nos devolvió el create
        BigDecimal expectedBalance = created.getBalance().getAmount();

        // --- 3) Consultar balance
        ResponseEntity<String> balanceResponse =
                admin.getForEntity(base + "/accounts/" + id + "/balance", String.class);
// Aquí imprimes el body crudo:
        System.out.println("RAW BODY -> `" + balanceResponse.getBody() + "`");
        assertThat(balanceResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        // Lo parseamos y lo comparamos con el balance devuelto en el create
        BigDecimal returned = new BigDecimal(balanceResponse.getBody().trim());
        assertThat(returned)
                .isEqualByComparingTo(expectedBalance);
    }
}
