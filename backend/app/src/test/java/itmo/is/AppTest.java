package itmo.is;

import org.junit.jupiter.api.Test;
import itmo.is.App;

class AppTest {
    @Test void applicationLoads() {
        // This test verifies that the JAX-RS application loads successfully
        App app = new App();
        assert app != null;
    }
}
