package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.Utilities;

import java.util.HashMap;
import java.util.List;

public class MoneyMatters extends CollegePage{
    WebDriver driver;
    WebDriverWait wait;

    private
    @FindBy(css = "a[href*=mailto]")
    WebElement email;

    public MoneyMatters(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);

        this.driver = driver;
        this.wait = wait;
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, 15), this);
    }


    private void openMoneyMattersTab(){
        String collegePageURLtoShorten = driver.getCurrentUrl();
        int i = collegePageURLtoShorten.lastIndexOf("/");
        String collegePageURL = collegePageURLtoShorten.substring(0, i);
        String admissionsTab = "?tab=profile-money-tab";
        String urlOpen = collegePageURL + admissionsTab;
        driver.get(urlOpen);
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector("div > dl.dl-split-sm")));

    }

    private String getEmail(){
        String string = "";

        if(Utilities.checkIfElementExists(driver, "a[href*=mailto]")) {
            try {
                string = email.getText();
            } catch (NoSuchElementException e) {
                e.printStackTrace();
            }
        }
        System.out.println("E-mail: " + string);

        return string;
    }

    public HashMap<String, String> moneyMattersTableData(){
        openMoneyMattersTab();

        HashMap<String, String> tableData = new HashMap<String, String>();
        List<WebElement> tableElements = driver.findElements(By.cssSelector("div > dl.dl-split-sm"));
        String labelCostAttendance = "Cost of Attendance", labelTuitionFees = "Tuition and Fees",
                labelRoomBoard = "Room and Board", labelBooksSupplies = "Books and Supplies",
                labelOtherExpenses = "Other Expenses", labelEmail = "E-mail";

        WebElement tuitionExpenses = tableElements.get(0);
        String costAttendance = super.getValueFromLabel(tuitionExpenses, labelCostAttendance);
        tableData.put(labelCostAttendance, costAttendance);
        String tuitionFees = super.getValueFromLabel(tuitionExpenses, labelTuitionFees);
        tableData.put(labelTuitionFees, tuitionFees);
        String roomBoard = super.getValueFromLabel(tuitionExpenses, labelRoomBoard);
        tableData.put(labelRoomBoard, roomBoard);
        String booksSupplies = super.getValueFromLabel(tuitionExpenses, labelBooksSupplies);
        tableData.put(labelBooksSupplies, booksSupplies);
        String otherExpenses = super.getValueFromLabel(tuitionExpenses, labelOtherExpenses);
        tableData.put(labelOtherExpenses, otherExpenses);

        WebElement financialAid = tableElements.get(1);
        String email = getEmail();
        tableData.put(labelEmail, email);



        return tableData;
    }



}
