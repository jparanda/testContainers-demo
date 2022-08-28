package org.juan.learn.testcontainers;

import org.junit.After;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockserver.client.MockServerClient;
import org.mockserver.model.Header;
import org.mockserver.model.HttpRequest;
import org.mockserver.model.HttpResponse;
import org.mockserver.model.JsonBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(
        classes = TestcontainersMockserverApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
class CustomerControllerIntegrationTest extends Containers {

    @LocalServerPort
    private int localServerPort;
    private static RestTemplate restTemplate;
    private static MockServerClient emailService;

    @Autowired
    private CustomerRepository repository;


    @BeforeAll
    public static void before() {
        emailService = new MockServerClient(Containers.EMAIL_SERVICE.getHost(),
                Containers.EMAIL_SERVICE.getServerPort());
        emailService.when(HttpRequest.request("/send"))
                .respond(HttpResponse.response().withStatusCode(204));
    }

    @Test
    void createTest() {
        // Arrange
        String email = "jessy@example.com";
        String firstName = "Jessy";
        String lastName = "Aranda";

        restTemplate = new RestTemplateBuilder()
                .rootUri("http://localhost:" + localServerPort + "/customer-service/api/v1")
                .build();

        Map<String, String> request = Map.ofEntries(
                Map.entry("email", email),
                Map.entry("firstName", firstName),
                Map.entry("lastName", lastName)
        );

        // Act
        restTemplate.postForEntity("/customers", request, Void.class);

        // Asserts and Verifies
        emailService.verify(HttpRequest.request()
                .withPath("/send")
                .withMethod("POST")
                .withHeader(Header.header("Content-Type", "application/json"))
                .withBody(JsonBody.json(new EmailService.SendEmailRequest("WELCOME", email)))
        );

        Customer customer = repository.findByEmail(email).orElseThrow();

        assertThat(customer.getEmail()).isEqualTo(email);
        assertThat(customer.getFirstName()).isEqualTo(firstName);
    }

    @After
    public void after() {
        repository.deleteAll();
        emailService.stop();
    }
}