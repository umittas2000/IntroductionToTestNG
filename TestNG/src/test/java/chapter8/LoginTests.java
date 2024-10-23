package chapter8;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;

public class LoginTests {
    private WebDriver driver;

    @BeforeTest
    public void setUp() {

    }

    @BeforeMethod
    public void login() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://practicetestautomation.com/practice-test-login/");
    }

    @AfterMethod
    public void afterMethod() {
        driver.close();
    }

    @Test(enabled = false, dataProviderClass = LoginDP.class,dataProvider = "login-data")
    public void testDataFlow(String username, String password,boolean expected)
    {
        System.out.println("Test Data: "+ username);
    }

    @Test(dataProviderClass = LoginDP.class,dataProvider = "login-data")
    public void testLogin(String username, String password,boolean expected)
    {
        driver.findElement(By.id("username")).sendKeys(username);
        driver.findElement(By.id("password")).sendKeys(password);
        driver.findElement(By.id("submit")).click();
        String actualURL = driver.getCurrentUrl();
        String expectedURL = "https://practicetestautomation.com/logged-in-successfully/";

        Assert.assertEquals(expected,(actualURL.equals(expectedURL)),"Wrong URL");
    }
}
