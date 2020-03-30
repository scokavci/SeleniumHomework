package com.automation.homework_4TH;

import org.openqa.selenium.*;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import utilities.BrowserUtils;
import utilities.DriverFactory;

import java.sql.Driver;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class AmazonTests {
    private WebDriver driver;
    private By searchBy = By.cssSelector("input[type='text']");

    @BeforeMethod
    public void setup(){
        driver= DriverFactory.createDriver("chrome");
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get("https://amazon.com");
    }
    /**
     * go to https://amazon.com
     * search for "wooden spoon"
     * click search
     * remember the name and the price of a random result
     * click on that random result
     * verify default quantity of items is 1
     * verify that the name and the price is the same as the one from step 5
     * verify button "Add to Cart" is visible
     */
    @Test(description="verify added item to cart")
    public void cart(){
        driver.findElement(searchBy).sendKeys("wooden spoon", Keys.ENTER);
        //OXO 1130880 Good Grips Wooden Corner Spoon & Scraper,Brown 5.99
        driver.findElement(By.partialLinkText("OXO 1130880 Good Grips Wooden")).click();

        WebElement quantitybox = driver.findElement(By.xpath("//span[@class='a-dropdown-prompt']"));
        String actualQuantity = quantitybox.getText();
        String expectedQuantity = "1";
        Assert.assertEquals(actualQuantity,expectedQuantity,"it's not matching");

        WebElement price = driver.findElement(By.xpath("//span[@id='price_inside_buybox']"));
        String actualPrice = price.getText();
        String expectedPrice="$5.99";
        Assert.assertEquals(actualPrice,expectedPrice);

        WebElement name = driver.findElement(By.xpath("//span[@id='productTitle']"));
        String actualName = name.getText();
        String expectedName = "OXO 1130880 Good Grips Wooden Corner Spoon & Scraper,Brown";
        Assert.assertEquals(actualName,expectedName);

        WebElement addCart = driver.findElement(By.cssSelector("input[id='add-to-cart-button']"));
        Assert.assertTrue(  addCart.isDisplayed()  );
    }

    /**
     * remember name first result that has prime label
     *  select Prime checkbox on the left
     * verify that name first result that has prime label is same as step 4
     * check the last checkbox under Brand on the left
     *  verify that name first result that has prime label is different
     */

    @Test
    public void prime(){
        driver.findElement(searchBy).sendKeys("wooden spoon", Keys.ENTER);

        WebElement firstPrimeName =driver.findElement(By.xpath("(//i[@aria-label='Amazon Prime']/../../../../../..//h2)[1]"));
        String name1 = firstPrimeName.getText();

        driver.findElement(By.xpath("//i[@class='a-icon a-icon-prime a-icon-medium']/../div/label/i")).click();

        String name2 = driver.findElement(By.xpath("(//i[@aria-label='Amazon Prime']/../../../../../..//h2)[1]")).getText();

        Assert.assertEquals(name2, name1);
                                                                  //last item in  list of brand
        driver.findElement(By.xpath("//div[@id='brandsRefinements']//ul/li[last()]//i")).click();
        String name3 = driver.findElement(By.xpath("(//i[@aria-label='Amazon Prime']/../../../../../..//h2)[1]")).getText();

        System.out.println(name1);
        System.out.println(name2);
        System.out.println(name3);

        Assert.assertNotEquals(name1, name3);
    }

    /**
     * remember all Brand names on the left
     * select Prime checkbox on the left
     * verify that same Brand names are still displayed
     */
    @Test(description="verify brand names displayed" )
    public void moreSpoons() {
        driver.findElement(searchBy).sendKeys("wooden spoon", Keys.ENTER);
        List<WebElement> listOfBrand = driver.findElements(By.xpath("//div[@id='brandsRefinements']//ul/li/span/a/span"));

        List<String> brandNames = new ArrayList<>();
        for(WebElement eachBrand : listOfBrand){
            brandNames.add(eachBrand.getText());
        }
                                       // primelabel box element                  child to parent
        driver.findElement(By.xpath("//i[@class='a-icon a-icon-prime a-icon-medium']/../div/label/i")).click();
                                        //*[@id="p_85/2470955011"]/span/a/div/label/i
                                                                                                           //  go to any child  ;go to direct child
        List<WebElement> listOfBrandsAfterClickPrime= driver.findElements(By.xpath("//div[@id='brandsRefinements']//ul/li/span/a/span"));

        List<String > brandNamesAfterClickPrime = new ArrayList<>();
        for(WebElement each : listOfBrandsAfterClickPrime){
            brandNamesAfterClickPrime.add(each.getText());
        }
        Assert.assertEquals(brandNames,brandNamesAfterClickPrime);
    }


    @Test(description="verify results less than $25")
    public void test4() {
        driver.findElement(searchBy).sendKeys("wooden spoon", Keys.ENTER);
        BrowserUtils.wait(4);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,1000)");
        WebElement priceBox = driver.findElement(By.xpath("//span[@class='a-size-base a-color-base' and contains(text(),'Under $25')]"));
        js.executeScript("arguments[0].click",priceBox);
        List<WebElement> prices = driver.findElements(By.xpath("//span[@class='a-offscreen']"));
        List<String> pricesList = BrowserUtils.getTextFromWebElements(prices);
        String expected ="$25";
        for (String eachPrice : pricesList) {
             Assert.assertTrue(eachPrice.compareTo(expected) < 0);
        }
    }
    @AfterMethod
    public void teardown(){
        driver.quit();
    }
}
