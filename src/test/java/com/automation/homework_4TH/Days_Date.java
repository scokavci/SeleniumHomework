package com.automation.homework_4TH;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import utilities.BrowserUtils;
import utilities.DriverFactory;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Days_Date {
    private WebDriver driver;

    @BeforeMethod
    public void setup(){
        driver = DriverFactory.createDriver("chrome");
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }
    /*
   1.go to http://samples.gwtproject.org/samples/Showcase/Showcase.html#!CwCheckBox
   2.Randomly select a checkbox. As soon as you check any day,
   print the name of the day and uncheck immediately.
   After you check and uncheck Friday for the third time, exit the program.
    */
    @Test(description="randomly select checkbox, print the name of day")
    public void checkUncheckDays(){
        driver.get("http://samples.gwtproject.org/samples/Showcase/Showcase.html#!CwCheckBox");
        List<WebElement> boxes = driver.findElements(By.cssSelector("input[type='checkbox']"));
        List<WebElement> labeldays = driver.findElements(By.xpath("//input[@type='checkbox']//following-sibling::label "));
        Random rand = new Random();
        int count = 0;
              while(count<3){
                    //this method will return any value between 0 and 7
                      int index = rand.nextInt(labeldays.size());

                      if( boxes.get(index).isEnabled()){
                          labeldays.get(index).click();
                          if( labeldays.get(index).getText().equals("Friday")){
                              count++;
                          }
                          System.out.println(labeldays.get(index).getText());
                          // use click again to make it unchecked
                          labeldays.get(index).click();
                      }

                  }
             }
              /*
              1.go to http://practice.cybertekschool.com/dropdown
              2.verify that dropdowns under Select your date of birth display current year,month,day
              */
    @Test
    public void verifyTodaysDate(){
        driver.get("http://practice.cybertekschool.com/dropdown");
        WebElement year = driver.findElement(By.xpath("//select[@id='year']"));
        WebElement month = driver.findElement(By.xpath("//select[@id='month']"));
        WebElement day = driver.findElement(By.xpath("//select[@id='day']"));
        //dropdown has select; so create select object
        Select selectYear = new Select(year);
        Select selectMonth = new Select(month);
        Select selectDay = new Select(day);

        String firstselectedYear = selectYear.getFirstSelectedOption().getText();
        String firstselectedMonth = selectMonth.getFirstSelectedOption().getText();
        String firstselectedDay =selectDay.getFirstSelectedOption().getText();

        String expectedDate = LocalDate.now().format(DateTimeFormatter.ofPattern("MMMM dd, yyyy"));
        String actualDate = firstselectedMonth+" "+firstselectedDay+", "+firstselectedYear;
        Assert.assertEquals(actualDate,expectedDate);
    }
      /*1.go to http://practice.cybertekschool.com/dropdown
    2.select a random year under Select your date of birth
    3.select month January
    4.verify that days dropdown has current number of days
    5.repeat steps 3, 4 for all the months
    NOTE: if you randomly select a leap year, verify February has 29 days
    ****February has 29 days 28 days, some months 30 and 31 days.
     */

    @Test (description= "verify days for all months")
    public void verifyDays(){
        driver.get("http://practice.cybertekschool.com/dropdown");
        WebElement year = driver.findElement(By.xpath("//select[@id='year']"));
        WebElement month = driver.findElement(By.xpath("//select[@id='month']"));
        WebElement day = driver.findElement(By.xpath("//select[@id='day']"));
        //dropdown has select
        Select selectYear = new Select(year);
        Select selectMonth = new Select(month);
        Select selectDay = new Select(day);

        //randomly select a year
        Random rand = new Random();
        int index = rand.nextInt(selectYear.getOptions().size());// from 0 to number of years
        selectYear.selectByIndex(index);

        // months have 31 days only.
        String[] monthsOf31Days = {"January","March","May","July","August","October","December"};
        List<String> monthsOf31DaysList = new ArrayList<>(Arrays.asList(monthsOf31Days));

        int febDays;

        int yearAsNumber = Integer.parseInt(  selectYear.getFirstSelectedOption().getText() );
        //february days depend on  year
        if(  yearAsNumber%400 == 0 || yearAsNumber%4 == 0 && yearAsNumber%100 !=0 ){
            febDays=29;
        }else{
            febDays=28;
        }

        for (int i = 0; i <12 ; i++) {
            //select month by index
            selectMonth.selectByIndex(i);

            if (monthsOf31DaysList.contains(selectMonth.getFirstSelectedOption().getText())) {
                Assert.assertEquals(selectDay.getOptions().size(), 31);
            } else if (selectMonth.getFirstSelectedOption().getText().equals("February")) {
                Assert.assertEquals(selectDay.getOptions().size(), febDays);
            } else {
                Assert.assertEquals(selectDay.getOptions().size(), 30);
            }
        }

    }

    @AfterMethod
    public void teardown(){
        driver.quit();
    }

}
