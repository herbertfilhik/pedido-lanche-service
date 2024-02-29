package com.exemplo.lanchonete.consumer;

import au.com.dius.pact.consumer.MockServer;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.core.model.annotations.Pact;

import org.apache.http.client.fluent.Request;
import org.apache.http.entity.ContentType;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(PactConsumerTestExt.class)
public class PedidoConsumerTest {
	private static final String ENDPOINT_PEDIDO = "/pedido";
	private static final String CORPO_REQUISICAO = "{\"item\":\"hamburguer\",\"extras\":\"queijo\",\"semCebola\":true}";
	private static final String RESPOSTA_ESPERADA = "{\"status\":\"Pedido aceito\"}";

	@Pact(consumer = "ClientePedido")
	public RequestResponsePact createPact(PactDslWithProvider builder) {
		return builder.given("Default order").uponReceiving("An order for a hamburger with cheese and without onion x")
				.path(ENDPOINT_PEDIDO).method("POST").body(CORPO_REQUISICAO, ContentType.APPLICATION_JSON)
				.willRespondWith().status(200).body(RESPOSTA_ESPERADA).toPact();
	}

	@Test
	@PactTestFor(pactMethod = "createPact")
	void testMakeOrder(MockServer mockServer) throws IOException {
		String url = mockServer.getUrl() + ENDPOINT_PEDIDO;
		HttpResponse response = sendOrder(url);
		verifyResponse(response);
	}

	private HttpResponse sendOrder(String url) throws IOException {
		return Request.Post(url).bodyString(CORPO_REQUISICAO, ContentType.APPLICATION_JSON).execute().returnResponse();
	}

	private void verifyResponse(HttpResponse response) throws IOException {
		assertEquals(200, response.getStatusLine().getStatusCode());
		String responseBody = EntityUtils.toString(response.getEntity());
		assertEquals(RESPOSTA_ESPERADA, responseBody);
	}
}
