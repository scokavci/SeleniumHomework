package com.automation.homework_3RD;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import utilities.BrowserUtils;


public class TestCasesFrom1To5 {
    private WebDriver driver;
    private String URL ="http://practice.cybertekschool.com";
    private By regisFormBy = By.xpath("//a[text()='Registration Form'] ");
    private By birthdayBy = By.name("birthday");
    private By cplusBy = By.xpath("//label[text()= 'C++']");
    private By javaBy = By.xpath("//label[text()= 'Java']");
    private By javascriptBy = By.xpath("//label[text()= 'JavaScript']");
    private By firstnameBy = By.name("firstname");
    private By lastnameBy = By.name("lastname");
    private By usernameBy = By.name("username");
    private By emailBy = By.name("email");
    private By passwordBy = By.name("password");
    private By phoneBy = By.name("phone");
    private By maleBy = By.xpath("//input[@value='male']");
    private By femaleBy = By.cssSelector("input[value='female']");
    private By otherBy = By.cssSelector("input[value='other']");
    private By departmentBy = By.name("department");
    private By jobtitleBy = By.name("job_title");
    private By signupBy = By.id("wooden_spoon");



    //TestCase #1
    @Test (description=" Verify warning message displayed in dateofbirth box ")
    public void verifyMessageInDateOfBirth(){
        driver.findElement(birthdayBy).sendKeys("wrong_dob", Keys.ENTER );
        BrowserUtils.wait(3);
        WebElement message = driver.findElement(By.xpath("//small[@class='help-block' and contains(text(),'The date of birth is not valid')]"));
        BrowserUtils.wait(3);
        String actual = message.getText();
        String expected = "The date of birth is not valid";
        Assert.assertEquals(actual,expected,"The message is not matching");
    }
    //TestCase #2
    @Test (description = "verify languages displayed")
    public void verifyLanguagesDisplay(){
        Assert.assertTrue( driver.findElement(cplusBy).isDisplayed() );
        Assert.assertTrue( driver.findElement(javaBy).isDisplayed() );
        Assert.assertTrue( driver.findElement(javascriptBy).isDisplayed() );
    }
    //TestCase #3
    @Test
    public void verifyMsgInNameBox(){
        driver.findElement(firstnameBy).sendKeys("b",Keys.ENTER);
        Assert.assertTrue( driver.findElement(By.xpath("//small[text()='first name must be more than 2 and less than 64 characters long']")).isDisplayed());
    }
    //TestCase #4
    @Test
    public void verifyMsgInLastName(){
        driver.findElement(lastnameBy).sendKeys("g",Keys.ENTER);
        Assert.assertTrue( driver.findElement(By.xpath("//small[text()='The last name must be more than 2 and less than 64 characters long']")).isDisplayed());
    }
    //TestCase #5
    @Test
    public void verifyCompleteRegistration(){
        driver.findElement(firstnameBy).sendKeys("Tom");
        driver.findElement(lastnameBy).sendKeys("Smith");
        driver.findElement(usernameBy).sendKeys("tsmith");
        driver.findElement(emailBy).sendKeys("tsmith@gmail.com");
        driver.findElement(passwordBy).sendKeys("12345678");
        driver.findElement(phoneBy).sendKeys("678-234-2345");
        driver.findElement(femaleBy).click();
        driver.findElement(birthdayBy).sendKeys("12/3/2010");

        Select depselect = new Select(driver.findElement(departmentBy));
        depselect.selectByVisibleText("Department of Engineering");

        Select jobselect = new Select(driver.findElement(jobtitleBy));
        jobselect.selectByVisibleText("QA");

        driver.findElement(javaBy).click();
        driver.findElement(signupBy).click();

        String expected = "You've successfully completed registration!";
        String actual = driver.findElement(By.tagName("p")).getText();
        BrowserUtils.wait(3);
        Assert.assertEquals(actual,expected,"it's not matching");

    }

    @BeforeMethod
    public void setup(){
        WebDriverManager.chromedriver().version("79").setup();
        driver = new ChromeDriver();
        driver.get(URL);
        driver.manage().window().maximize();
        driver.findElement(regisFormBy).click();
        BrowserUtils.wait(3);
    }
    @AfterMethod
    public void teardown(){
        driver.quit();
    }

}

