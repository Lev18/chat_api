import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

@QuarkusTest
@ApplicationScoped
public class ChatResourceTest {

    @Test
    public void testGet() {
        given()
          .when().get("/chat")
          .then()
             .statusCode(200)
             .body(is("Please login or register."));
    }
}

