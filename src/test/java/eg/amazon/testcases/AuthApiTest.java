package eg.amazon.testcases;

import eg.amazon.base.BaseApiTest;
import org.testng.Assert;
import org.testng.annotations.Test;

public class AuthApiTest extends BaseApiTest {

    @Test(description = "Verify that a valid token is generated")
    public void testTokenGeneration() {
        Assert.assertNotNull(token, "Token should not be null.");
        Assert.assertTrue(token.length() > 10, "Token length should be greater than 10 characters.");
    }
}
