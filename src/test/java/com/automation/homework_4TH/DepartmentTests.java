package com.automation.homework_4TH;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import utilities.BrowserUtils;
import utilities.DriverFactory;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class DepartmentTests {
   private WebDriver driver;
   private Actions action;

    @BeforeMethod
    public void setup(){
        driver = DriverFactory.createDriver("chrome");
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    /**
     * go to https://www.amazon.com
     *  verify that default value of the All departments dropdown is All
     *  verify that options in the All departments dropdown are not sorted alphabetically
     */

    @Test(description="Verify departments not sorted alphabetically")
    public void verifyDepartment() {
        driver.get("https://www.amazon.com");
        WebElement all = driver.findElement(By.cssSelector("span[class='nav-search-label']"));
        String expected = "All";
        String actual = all.getText();
        Assert.assertEquals(actual, expected);

        WebElement search = driver.findElement(By.cssSelector("select[class='nav-search-dropdown searchSelect']"));
        Select selectdept = new Select(search);
        List<WebElement> selectlistOfDept = selectdept.getOptions();

        boolean notalphaticOrder = false;

        for (int i = 0; i < selectlistOfDept.size() - 1; i++) {
            // a < b should be smaller.
            if (selectlistOfDept.get(i).getText().compareTo(selectlistOfDept.get(i + 1).getText()) > 0) {
                notalphaticOrder = true;
                break;
            }
        }
        Assert.assertTrue(notalphaticOrder);
    }

    /**
     * go to https://www.amazon.com/gp/site-directory
     * verify that every main department name (indicated by blue rectangles in the picture) is
     * present in the All departments dropdown (indicated by green rectangle in the picture)
     */
    @Test
    public void main_departments(){
        driver.get("https://www.amazon.com/gp/site-directory");
        List<WebElement> mainDepartment = driver.findElements(By.tagName("h2"));
        List<WebElement> allDepartment = new Select(driver.findElement(By.cssSelector("[class='nav-search-dropdown searchSelect']"))).getOptions();

        Set<String> mainDepNames = new HashSet<>();//unique names
        for( WebElement each : mainDepartment){
            mainDepNames.add(each.getText());
        }
        Set<String> allDepNames = new HashSet<>();
        for( WebElement eachDepart : allDepartment){
            allDepNames.add(eachDepart.getText());
        }
        for(String each : mainDepNames){
            if(!allDepNames.contains(each)){
                System.out.println(each);
                System.out.println("This main dep is not in All departments list");
            }
        }
        Assert.assertTrue(!allDepartment.containsAll(mainDepNames));
    }

    @AfterMethod
    public void teardown(){
        driver.quit();
    }
}
