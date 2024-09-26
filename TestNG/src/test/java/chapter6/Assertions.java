package chapter6;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;

public class Assertions {
    /**
     * Assertions has 3 parameters
     * Expected result, Actual result and the message if test fails.

     * Junit tests can be migrated into TestNG and they will continue to run
     * without any additional work with org.testng.AssertJunit class.

     * org.testng.AssertJunit
     * org.testng.Assert

     * Asserts will compare Actual and expected value
     * and make your test pass or fail based on output.
     */

    //driver will be used by test methods, so I keep is in class level.
    WebDriver driver;
    String username_txt;
    String password_txt;

    @BeforeClass
    public void setup(){
        //Driver configuration
        driver = new ChromeDriver();
        //Maximize Window
        driver.manage().window().maximize();
        //Wait maximum 5 seconds for each element interaction before throwing the error.
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

        //(AUT) Application Under Test URL
        driver.get("https://the-internet.herokuapp.com/login");

        username_txt="tomsmith";
        password_txt="SuperSecretPassword!";
    }

    @AfterClass
    public void tearDown(){
        driver.quit();
    }

    @Test(priority = 1)
    public void SignIn(){
        driver.findElement(By.id("username")).sendKeys(username_txt);
        driver.findElement(By.id("password")).sendKeys(password_txt);
        driver.findElement(By.xpath("//button[@type='submit']")).click();



        //Get the current page URL
        String actualPageUrl = driver.getCurrentUrl().toLowerCase();
        String ExpectedPageUrl = "https://the-internet.herokuapp.com/secure";

        //Print current page URL in console to check.
        System.out.println(actualPageUrl);

        //Compare Expected and Actual values with AssertEqual method
        Assert.assertEquals(actualPageUrl, ExpectedPageUrl,"Login Failed!");
    }

    @Test(priority = 2,dependsOnMethods = "SignIn")
    public void VerifySecureAreaMessage(){
        String ActualMessage = driver.findElement(By.xpath("//div[@id='flash-messages']")).getText();
        System.out.println(ActualMessage);

        String ExpectedMessage = "You logged into a secure area!\n" +
                "×";

        Assert.assertEquals(ActualMessage, ExpectedMessage,"Secure Area Message is not Correct!");
    }

    @Test(priority = 3, dependsOnMethods = "VerifySecureAreaMessage")
    public void UserSignout(){
        driver.findElement(By.xpath("//a[contains(@href,'logout')]")).click();

        String actualPageURL = driver.getCurrentUrl();
        String ExpectedPageURL = "https://the-internet.herokuapp.com/login";

        System.out.println(actualPageURL);

        Assert.assertEquals(actualPageURL, ExpectedPageURL,"User Signout Failed!");
    }
}
