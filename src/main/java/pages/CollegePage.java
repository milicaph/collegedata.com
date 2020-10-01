package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.Utilities;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class CollegePage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    private
    @FindBy(css = "a[href*=edu]")
    WebElement websiteURL;
   /* private
    @FindBy(xpath = "/html/body/div[1]/div[2]/main/div[2]/div/div/div/div/div/div[2]/div/dl[1]")
    WebElement testTable;*/

    public CollegePage(WebDriver driver, WebDriverWait wait){
        this.driver = driver;
        this.wait = wait;
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, 15), this);

    }

    public String getWebsite(){
        String string = "";

        try {
            string = websiteURL.getAttribute("href");
        } catch (Exception ignored){}

        System.out.println("WEBSITE: " + string);
        return string;
    }

    public String getPublicPrivate(){
        List<WebElement> statbarList = driver.findElements(By.cssSelector("div.statbar__item"));
        WebElement element = statbarList.get(0);

        String string = "";

        try {
            string = element.getText();
        } catch (Exception ignored){}

        System.out.println("PUBLIC OR PRIVATE: " + string);
        return string;
    }

    public String getCoEd(){
        List<WebElement> statbarList = driver.findElements(By.cssSelector("div.statbar__item"));
        WebElement element = statbarList.get(1);

        String string = "";

        try {
            string = element.getText();
        } catch (Exception ignored){}

        System.out.println("CO-ED: " + string);
        return string;
    }

    protected String getValueFromLabel(WebElement elementTable, String label){
        String string = "";

        try {
            string = Utilities.parseTableElement(elementTable, label);
        } catch (Exception ignored) {}

        System.out.println(label + ": " + string);

        return string;
    }

    public HashMap<String, String> overviewTableData(){
        HashMap<String, String> tableData = new HashMap<String, String>();
        List<WebElement> tableElements = driver.findElements(By.cssSelector("div > dl.dl-split-sm"));
        String labelEntrance = "Entrance Difficulty", labelRate = "Overall Admission Rate",
                labelDeadline = "Regular Admission Deadline", labelPopulation = "Population";
        WebElement admissions = tableElements.get(0);

        String difficulty = getValueFromLabel(admissions, labelEntrance);
        tableData.put(labelEntrance, difficulty);
        String rate = getValueFromLabel(admissions, labelRate);
        tableData.put(labelRate, rate);
        String deadline = getValueFromLabel(admissions, labelDeadline);
        tableData.put(labelDeadline, deadline);


        WebElement campus = tableElements.get(4);
        String population = getValueFromLabel(campus, labelPopulation);
        tableData.put(labelPopulation, population);

        //System.out.println(Collections.singletonList(tableData));

        return tableData;
    }









}
