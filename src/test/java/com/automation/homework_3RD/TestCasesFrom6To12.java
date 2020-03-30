package com.automation.homework_3RD;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import utilities.BrowserUtils;

public class TestCasesFrom6To12 {
    private WebDriver driver;
    private String URL = "https://practice-cybertekschool.herokuapp.com/";
    private String URL1="https://www.tempmailaddress.com";

    @Test(description="testcase #6")
    public void verifyEmailSet() {
        driver.get(URL1);
        WebElement emailbox = driver.findElement(By.cssSelector("span[id='email']"));
        String email = emailbox.getText();
        BrowserUtils.wait(3);
        driver.navigate().to(URL);
        BrowserUtils.wait(3);

        driver.findElement(By.xpath("//a[text()='Sign Up For Mailing List']")).click();
        driver.findElement(By.cssSelector("input[name='full_name']")).sendKeys("John Smith");
        driver.findElement(By.name("email")).sendKeys(email);
        driver.findElement(By.name("wooden_spoon")).click();

        WebElement signup = driver.findElement(By.cssSelector("h3[class='subheader']"));
        String actual = signup.getText();
        String expected = "Thank you for signing up. Click the button below to return to the home page.";
        Assert.assertEquals(actual,expected,"it's not matching");

        BrowserUtils.wait(3);

        driver.navigate().to(URL1);
        BrowserUtils.wait(3);
        WebElement emailfrom = driver.findElement(By.xpath("//tr/td[1]"));
        String actual1 = emailfrom.getText().trim();
        String expected1 = "do-not-reply@practice.cybertekschool.com";
        Assert.assertEquals(actual1,expected1,"it's not matching");

        BrowserUtils.wait(2);
        emailfrom.click();
        WebElement replyback = driver.findElement(By.cssSelector("span[id='odesilatel']"));
        String actual2 = replyback.getText();
        String expected2 = "do-not-reply@practice.cybertekschool.com";
        Assert.assertEquals(actual2,expected2,"it's not matching");

        WebElement thanks = driver.findElement(By.cssSelector("span[id='predmet']"));
        String actual3 = thanks.getText();
        String expected3 ="Thanks for subscribing to practice.cybertekschool.com!";
        Assert.assertEquals(actual3,expected3,"it's not matching");
    }
    @Test(description= "testcase #7")
    public void verifyUpload(){
        driver.get(URL);
        driver.findElement(By.linkText("File Upload")).click();
        driver.findElement(By.id("file-upload")).sendKeys("C:\\Users\\Lenovo\\Desktop\\SELENIUM\\classNotes\\day01.txt");
        BrowserUtils.wait(3);
        driver.findElement(By.id("file-submit")).click();
        WebElement uploadmessage = driver.findElement(By.tagName("h3"));
        String actual = uploadmessage.getText();
        String expected ="File Uploaded!";
        Assert.assertEquals(actual,expected,"it's not matching");
    }
    @Test(description="testcase #8")
    public void verifyAutoCompleteMessage(){
        driver.get(URL);
        BrowserUtils.wait(2);
        driver.findElement(By.linkText("Autocomplete")).click();
        driver.findElement(By.id("myCountry")).sendKeys("United States of America");
        driver.findElement(By.cssSelector("input[value='Submit']")).click();
        BrowserUtils.wait(4);
        WebElement message  = driver.findElement(By.cssSelector("p[id='result']"));
        String actual = message.getText();
        System.out.println(actual);
        BrowserUtils.wait(4);
        String expected = "You selected: United States of America";
        Assert.assertEquals(actual,expected,"it's not matching");

    }
    @Test (description="testCases #9-12 of verifying message ", dataProvider = "testData")
    public void verifyStatusMessage(String number, String message){
        driver.get(URL);
        driver.findElement(By.linkText("Status Codes")).click();
        driver.findElement(By.linkText(number)).click();
        BrowserUtils.wait(3);
        WebElement displaymessage = driver.findElement(By.tagName("p"));
        String actual = displaymessage.getText();
        String expected = message ;
        Assert.assertTrue(actual.contains(expected));
    }

    @DataProvider(name = "testData")
    public Object[][] testData(){
        return new Object[][]{ {"200","This page returned a 200 status code" },
                               {"301","This page returned a 301 status code" },
                               {"404","This page returned a 404 status code" },
                               {"500","This page returned a 500 status code" }
        };
    }


    @BeforeMethod
    public void setup(){
        WebDriverManager.chromedriver().version("79").setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }
    @AfterMethod
    public void teardown(){
        driver.quit();
    }

}

