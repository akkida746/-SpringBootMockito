package com.example.mocking;

import okhttp3.HttpUrl;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.Before;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.mockserver.MockServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

//https://www.baeldung.com/spring-mock-rest-template
@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
class MockingApplicationTests {

	@Value("${http://localhost:8080/greeting}")
	String testurl;
	@Mock
	RestTemplate restTemplate = new RestTemplate();
	MockRestServiceServer mockRestServiceServer;

	@Before
	public void init(){
		mockRestServiceServer = MockRestServiceServer.createServer(restTemplate);
	}

	@Test
	void serviceTest() throws IOException {
		Mockito.when(restTemplate.getForEntity(testurl, String.class))
		.thenReturn(new ResponseEntity("Mock Greeting", HttpStatus.OK));
		String response = restTemplate.getForEntity(testurl, String.class).getBody();
		System.out.println("Response: " + response);
	}
}
