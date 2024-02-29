package com.exemplo.lanchonete.provider;

import au.com.dius.pact.provider.junit5.HttpTestTarget;
import au.com.dius.pact.provider.junit5.PactVerificationInvocationContextProvider;
import au.com.dius.pact.provider.junitsupport.Provider;
import au.com.dius.pact.provider.junitsupport.State;
import au.com.dius.pact.provider.junitsupport.loader.PactFolder;
import au.com.dius.pact.provider.junit5.PactVerificationContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;

@Provider("NomeDoProvedor")
@PactFolder("target/pacts")
public class CozinhaProviderTest {

	@BeforeEach
	void before(PactVerificationContext context) {
		context.setTarget(new HttpTestTarget("localhost", 8080));
	}

	@TestTemplate
	@ExtendWith(PactVerificationInvocationContextProvider.class)
	void testTemplate(PactVerificationContext context) {
		context.verifyInteraction();
	}

	@State("Default order")
	public void toDefaultState() {
	}
}
