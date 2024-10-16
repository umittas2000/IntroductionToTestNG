package chapter7;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class DependencyTesting {

    @BeforeClass
    public void test1_SetUpChrome(){

    }

    @Test
    public void test2_OpenOrangeHRM(){

    }

    @Test
    public void test3_SignIn(){

    }

    @Test
    public void test4_SearchUser(){

    }

    @Test
    public void test5_SearchEmployee(){

    }

    @Test
    public void userSearch(){

    }

    @Test
    public void userSignOut(){

    }

    @AfterTest
    public void tearDown(){

    }
}
