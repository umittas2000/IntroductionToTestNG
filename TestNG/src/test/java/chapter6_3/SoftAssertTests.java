package chapter6_3;

import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.time.Duration;

public class SoftAssertTests {
    WebDriver driver;
    String username_txt;
    String password_txt;

    @BeforeClass
    public void setup(){
        //Driver configuration
        driver = new ChromeDriver();

        //if 2nd display on left, x will be minus number
        //if 2nd display on right, x will be positive number
        driver.manage().window().setPosition(new Point(-1500,0));//display 2

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
    public void SignIn() throws InterruptedException {
        driver.findElement(By.id("username")).sendKeys(username_txt);
        Thread.sleep(1000);
        driver.findElement(By.id("password")).sendKeys(password_txt);
        Thread.sleep(1000);
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        Thread.sleep(2000);



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

    /**
     * Soft Assert example will be applied in here
     * We will try 2 asserts in this test
     * 1 assert will verify logout message
     * 1 assert will verify currentURL after logout button click
     * Both assertions will be soft assert and if one fails,
     * Our codes will continue to run within test.
     */
    @Test(priority = 3, dependsOnMethods = "VerifySecureAreaMessage")
    public void UserSignout() throws InterruptedException {

        driver.findElement(By.xpath("//a[contains(@href,'logout')]")).click();
        Thread.sleep(2000);
        String actualPageURL = driver.getCurrentUrl();
        String ExpectedPageURL = "https://the-internet.herokuapp.com/login.";

        //Locate Logout message after signout
        String ActualSignoutMessage = driver.findElement(By.xpath("//div[@id='flash-messages']")).getText();
        String ExpectedSignoutMessage = "You logged out";
        System.out.println(actualPageURL);

        //initialize SoftAssert class
        SoftAssert softAssert = new SoftAssert();


        //Replace Assert class with SoftAssert
        softAssert.assertEquals(actualPageURL, ExpectedPageURL,"URL Fail!");
        //Just to show status of hard assert 1
        System.out.println("1. Soft Assert Verify Page URL after sign out!");

        //Replace Assert class with SoftAssert
        softAssert.assertTrue(ActualSignoutMessage.contains(ExpectedSignoutMessage),"Sign out message Fail!");
        //Just to show status of hard assert 2
        System.out.println("2. Soft Assert Verify Page URL after sign out!");

        //add this to end for proper assertions,
        // otherwise soft assertions will pass

        softAssert.assertAll();
    }
}
