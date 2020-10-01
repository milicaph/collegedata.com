package utils;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public class Utilities {

    public static void moveToElement(WebDriver driver, WebElement element){
        Actions action = new Actions(driver);
        action.moveToElement(element).perform();
    }

    public static String getURL(WebDriver driver){
        return driver.getCurrentUrl();
    }

    public static String parseTableElement(WebElement element, String labelDT){
        String endReference = "</dd>";
        String innerHTML = "";
        try{
            innerHTML = element.getAttribute("innerHTML");

        } catch (Exception ignored){}

        String string = "";
        try {
            String helperString = StringUtils.substringBetween(innerHTML, labelDT, endReference);
            int i = helperString.lastIndexOf(">") + 1;
            string = helperString.substring(i);

        } catch(Exception ignored){}


        return string;

    }

    public static boolean checkIfElementExists(WebDriver driver, String cssSelector){
        return driver.findElements(By.cssSelector(cssSelector)).size() != 0;

    }



}
