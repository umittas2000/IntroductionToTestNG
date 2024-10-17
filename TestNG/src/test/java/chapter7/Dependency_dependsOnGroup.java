package chapter7;

import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Dependency_dependsOnGroup {
    private WebDriver driver;
    private String url ="https://the-internet.herokuapp.com/login.";

    @BeforeClass()
    public void test1_SetUpChrome(){

        driver = new ChromeDriver();

        //if 2nd display on left, x will be minus number
        //if 2nd display on right, x will be positive number
        driver.manage().window().setPosition(new Point(-1500,0));//display 2

        driver.manage().window().maximize();
    }

    //@Test(groups = "SignIn Group")
    @Test(groups="loginState")
    public void test2_OpenLoginPage(){
        driver.get(url);
        String title = driver.getTitle();
        Assert.assertEquals(title, "The Internet");
        System.out.println("Test2. Open Login Page");
    }

    @Test(groups="loginState", dependsOnMethods = "test2_OpenLoginPage")
    public void test3_Login(){
        WebElement txtUsername = driver.findElement(By.id("username"));
        WebElement txtPassword = driver.findElement(By.id("password"));
        WebElement loginButton = driver.findElement(By.xpath("//button[@type='submit']"));
        txtUsername.sendKeys("tomsmith");
        txtPassword.sendKeys("SuperSecretPassword!");
        loginButton.click();

        System.out.println("Test3 Login");
    }

    @Test(dependsOnGroups = "loginState")
    public void test4_VerifyLoginMessage(){
        String loginMessage = driver.findElement(By.id("flash")).getText();
        String expectedMessage = "You logged into a secure area";
        //Assert.assertEquals(loginMessage, expectedMessage);
        Assert.assertTrue(loginMessage.contains(expectedMessage));
        System.out.println("Test4 Verify Login message");
    }

    @Test(dependsOnGroups = "loginState")
    public void test5_VerifySecureAreaMessage(){
        String secureAreaMessage = driver.findElement(By.xpath("//h4[@class='subheader']")).getText();
        String expectedMessage = "Welcome to the Secure Area. When you are done click logout below.";
        Assert.assertTrue(expectedMessage.contains(secureAreaMessage));
        System.out.println("Test5 Secure area Message");
    }

    @Test(dependsOnGroups="loginState")
    public void test6_Logout(){
        WebElement logoutButton = driver.findElement(By.xpath("//a[@href='/logout']"));
        logoutButton.click();

        String logoutMessage = driver.findElement(By.id("flash")).getText();
        String expectedMessage = "You logged out of the secure area";
        Assert.assertTrue(logoutMessage.contains(expectedMessage));
        System.out.println("Test6 Logout");
    }

    @AfterTest
    public void tearDown(){
      //  driver.quit();
    }
}
