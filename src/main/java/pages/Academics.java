package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Academics extends CollegePage{
    WebDriver driver;
    WebDriverWait wait;


    public Academics(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);

        this.driver = driver;
        this.wait = wait;
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, 15), this);
    }

    private void openAcademicsTab(){
        String collegePageURLtoShorten = driver.getCurrentUrl();
        int i = collegePageURLtoShorten.lastIndexOf("/");
        String collegePageURL = collegePageURLtoShorten.substring(0, i);
        String admissionsTab = "?tab=profile-academics-tab";
        String urlOpen = collegePageURL + admissionsTab;
        driver.get(urlOpen);
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector("ul.list--nice > li")));

    }

    public HashMap<String, ArrayList<String>> academicsTableData(){
        openAcademicsTab();

        HashMap<String, ArrayList<String>> tableData = new HashMap<String, ArrayList<String>>();
        List<WebElement> tableElements = driver.findElements(By.cssSelector("ul.list--nice > li"));
        ArrayList<String> undergradList = new ArrayList<String>();

        String undergradString = "";
        for(WebElement tableElement : tableElements){
            try{
                undergradString = tableElement.getText();
                if(!undergradString.contains("Not reported"))
                undergradList.add(undergradString);
            } catch (Exception ignored) {}
        }

        String labelUndergrads = "Undergraduate Majors";
        tableData.put(labelUndergrads, undergradList);

        System.out.print(tableData);


        return tableData;
    }

}
