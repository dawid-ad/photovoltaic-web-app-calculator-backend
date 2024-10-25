package pl.dawad.backend;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

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
