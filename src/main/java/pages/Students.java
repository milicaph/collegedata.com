package pages;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.HashMap;
import java.util.List;

public class Students extends CollegePage{
    WebDriver driver;
    WebDriverWait wait;


    public Students(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);

        this.driver = driver;
        this.wait = wait;
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, 15), this);
    }

    private void openStudentsTab(){
        String collegePageURLtoShorten = driver.getCurrentUrl();
        int i = collegePageURLtoShorten.lastIndexOf("/");
        String collegePageURL = collegePageURLtoShorten.substring(0, i);
        String admissionsTab = "?tab=profile-students-tab";
        String urlOpen = collegePageURL + admissionsTab;
        driver.get(urlOpen);
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector("div > dl.dl-split-sm")));


    }

    private String getManWoman(String innerHTML, String menWomen, String nextLabel){
        String start = menWomen + "</dt>";
        String end = null;

        if(menWomen.equals("Men") && nextLabel.equals("Students Enrolled"))
            end = "<dt>" + nextLabel;
        else if(menWomen.equals("Men") && nextLabel.equals("Full-Time Undergraduates")){
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


    public HashMap<String, String> studentsTableData(){
        openStudentsTab();

        HashMap<String, String> tableData = new HashMap<String, String>();
        List<WebElement> tableElements = driver.findElements(By.cssSelector("div > dl.dl-split-sm"));
        String labelCoeducational = "Coeducational", labelAllUndergrads = "All Undergraduates",
                labelWomen = "Women", labelMen = "Men", labelFTUndergrads = "Full-Time Undergraduates",
                labelEthnicity = "Ethnicity of Students from U.S.";


        WebElement studentBody = tableElements.get(0);
        String coeducational = super.getValueFromLabel(studentBody, labelCoeducational);
        tableData.put(labelCoeducational, coeducational);
        String allUndergrads = super.getValueFromLabel(studentBody, labelAllUndergrads);
        tableData.put(labelAllUndergrads, allUndergrads);
        String sBInnerHtml = studentBody.getAttribute("innerHTML");
        String women = getManWoman(sBInnerHtml, "Women", "Men");
        tableData.put(labelWomen + "SB", women);
        String men = getManWoman(sBInnerHtml, "Men", labelFTUndergrads);
        tableData.put(labelMen + "SB", men);
        String ftUndergrads = super.getValueFromLabel(studentBody, labelFTUndergrads);
        tableData.put(labelFTUndergrads, ftUndergrads);
        String ethnicity = super.getValueFromLabel(studentBody, labelEthnicity);
        tableData.put(labelEthnicity, ethnicity);



        return tableData;
    }



}
