package chapter8;

import org.testng.annotations.DataProvider;

/**
 * in order to access DataProvider class within our test methods,
 * Data Provider method must be static
 */
public class LoginDP {

    @DataProvider(name="login-data")
    public static Object[][] loginData() {
        Object[][] data = new Object[3][3];
        data[0][0] = "admin"; data[0][1] = "admin"; data[0][2] = false;
        data[1][0] = "test"; data[1][1] = "test"; data[1][2] = false;
        data[2][0] = "student"; data[2][1] = "Password123"; data[2][2] = true;
        return data;
    }
}
