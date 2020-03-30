package com.automation.homework_4TH;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import utilities.BrowserUtils;
import utilities.DriverFactory;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class LinkTests {
   private WebDriver driver;

   @BeforeMethod
    public void setup(){
       driver = DriverFactory.createDriver("chrome");
       driver.manage().window().maximize();
       driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
   }

    /**
     * go to https://www.w3schools.com/
     *  find all the elements in the page with the tag a
     *  for each of those elements, if it is displayed on the page, print the text and the href of that
     * element.
     */

   @Test(description="find all elements with tag a, print text and href of element")
   public void links(){
       driver.get("https://www.w3schools.com/");
       List<WebElement> elementsOfa = driver.findElements(By.tagName("a"));
       for (WebElement eachaElement : elementsOfa) {
           if (eachaElement.isDisplayed()) {
               System.out.println(eachaElement.getText());
               System.out.println(eachaElement.getAttribute("href"));
           }
       }
   }

    /**
     * go to https://www.selenium.dev/documentation/en/
     * find all the elements in the page with the tag a
     * verify that all the links are valid
     */

   @Test(description="find elements with tag a and verify all links valid")
   public void validLinks(){
    driver.get("https://www.selenium.dev/documentation/en/");

    List<WebElement> links = driver.findElements(By.tagName("a"));
       for (WebElement eachLink : links) {
           String urlOfEachLink = eachLink.getAttribute("href");
           System.out.println(urlOfEachLink +" is enabled: "+ eachLink.isEnabled());
       }
   }


   @AfterMethod
    public void teardown(){
       driver.quit();
   }

}
