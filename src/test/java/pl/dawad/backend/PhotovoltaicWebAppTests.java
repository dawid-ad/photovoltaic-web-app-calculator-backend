package pl.dawad.backend;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import pl.dawad.backend.model.ContactForm;

@SpringBootTest
class PhotovoltaicWebAppTests {
	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testCalculation() {
	}

}
