package pages;

import org.apache.commons.lang3.StringUtils;
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

public class CollegeAdmissions extends CollegePage {
    WebDriver driver;
    WebDriverWait wait;

    private
    @FindBy(css = "a[href*=mailto]")
    WebElement email;

    public CollegeAdmissions(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);

        this.driver = driver;
        this.wait = wait;
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, 15), this);

    }

    private void openAdmissionsTab(){
        String collegePageURL = driver.getCurrentUrl();
        String admissionsTab = "?tab=profile-admission-tab";
        String urlOpen = collegePageURL + admissionsTab;
        driver.get(urlOpen);
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector("div > dl.dl-split-sm")));


    }

    private String getManWoman(String innerHTML, String menWomen, String nextLabel){
        String start = menWomen + "</dt>";
        String end = null;

        if(menWomen.equals("Men") && nextLabel.equals("Students Enrolled"))
            end = "<dt>" + nextLabel;
        else if(menWomen.equals("Men") && nextLabel.equals("Early Decision Admission Rate")){
            start = "<dt class=\"dl-split-sm__indent\">Men";
            end = "</dd>";
        } else if(menWomen.equals("Women") && nextLabel.equals("Men"))
            end = "</dd>";
        String stringHTML = StringUtils.substringBetween(innerHTML, start, end);
        String string = "";
        if(stringHTML.contains(">") || stringHTML.contains("<") ) {
            string = stringHTML.replaceAll("\\<[^>]*>","")
                               .trim();
            System.out.println(menWomen + ": " + string);
            return string;

        } else {
            System.out.println(menWomen + ": " + StringUtils.substringBetween(innerHTML, start, end));
            return stringHTML;

        }
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

    public HashMap<String, String> admissionsTableData(){
        openAdmissionsTab();

        HashMap<String, String> tableData = new HashMap<String, String>();
        List<WebElement> tableElements = driver.findElements(By.cssSelector("div > dl.dl-split-sm"));
        String labelAddress = "Address", labelCityStateZip = "City, State, Zip",
                labelPhone = "Phone", labelFax = "Fax", labelEmail = "E-mail",
                labelApplicationFee = "Application Fee", labelOverallAdmissionsRate = "Overall Admission Rate",
                labelWomen = "Women", labelMen = "Men", labelStudents = "Students Enrolled", labelSATMath = "SAT Math",
                labelSATEBWR = "SAT EBRW", labelACT = "ACT Composite", labelHighSchool = "High School Class Rank" ;

        WebElement admissionsOffice = tableElements.get(1);
        String address = super.getValueFromLabel(admissionsOffice, labelAddress);
        tableData.put(labelAddress, address);
        String cityZipState = super.getValueFromLabel(admissionsOffice, labelCityStateZip);
        tableData.put(labelCityStateZip, cityZipState);
        String phone = super.getValueFromLabel(admissionsOffice, labelPhone);
        tableData.put(labelPhone, phone);
        String fax = super.getValueFromLabel(admissionsOffice, labelFax);
        tableData.put(labelFax, fax);
        String email = getEmail();
        tableData.put(labelEmail, email);

        WebElement applicationFees = tableElements.get(2);
        String applicationFee = super.getValueFromLabel(applicationFees, labelApplicationFee);
        tableData.put(labelApplicationFee, applicationFee);

        WebElement admissionProfile = tableElements.get(6);
        String admissionsRate = super.getValueFromLabel(admissionProfile, labelOverallAdmissionsRate);
        tableData.put(labelOverallAdmissionsRate, admissionsRate);
        String aPInnerHtml = admissionProfile.getAttribute("innerHTML");
        String arWomen = getManWoman(aPInnerHtml, "Women", "Men");
        tableData.put(labelWomen + "OAR", arWomen);
        String arMen = getManWoman(aPInnerHtml, "Men", labelStudents);
        tableData.put(labelMen + "OAR", arMen);
        String studentsEnrolled = super.getValueFromLabel(admissionProfile, labelStudents);
        tableData.put(labelWomen + "SE", studentsEnrolled);
        String seWomen = getManWoman(aPInnerHtml,"Women", "Men");
        tableData.put(labelWomen + "SE", seWomen);
        String seMen = getManWoman(aPInnerHtml,"Men", "Early Decision Admission Rate");
        tableData.put(labelMen + "SE", seMen);

        WebElement satScores = tableElements.get(8);
        String satMath = super.getValueFromLabel(satScores, labelSATMath);
        tableData.put(labelSATMath, satMath);
        String satEBRW = super.getValueFromLabel(satScores, labelSATEBWR);
        tableData.put(labelSATEBWR, satEBRW);

        WebElement actScores = tableElements.get(9);
        String actComposite = super.getValueFromLabel(actScores, labelACT);
        tableData.put(labelACT, actComposite);

        WebElement otherFreshmen = tableElements.get(9);
        String otherQualifications = super.getValueFromLabel(otherFreshmen, labelHighSchool);
        tableData.put(labelHighSchool,otherQualifications);

        return tableData;
    }












}
