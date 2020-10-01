package com.company;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.*;
import parameters.ReadExcel;
import settings.DriverSettings;

import java.util.ArrayList;

public class Scraper {

    public static void main(String[] args) {
        DriverSettings initiateDriver = new DriverSettings();

        WebDriver driver = initiateDriver.getDriver();
        driver.manage().window().maximize();

        WebDriverWait wait = new WebDriverWait(driver, 60);
        ArrayList<String> collegeCities = new ArrayList<String>();
        ArrayList<String> namesOfColleges = new ArrayList<String>();
        ReadExcel.getCollegeNames(collegeCities, namesOfColleges);

        System.out.print(namesOfColleges);
    int i = 0;
    for (String collegeName : namesOfColleges) {
            String city = "";
        try {
            city = collegeCities.get(i);
            SearchPage searchPage = new SearchPage(driver, wait);
            searchPage.findCollege(collegeName, city);

            CollegePage collegePage = new CollegePage(driver, wait);
            collegePage.getWebsite();
            collegePage.getPublicPrivate();
            collegePage.getCoEd();
            collegePage.overviewTableData();

            CollegeAdmissions admissions = new CollegeAdmissions(driver, wait);
            admissions.admissionsTableData();

            MoneyMatters moneyMatters = new MoneyMatters(driver, wait);
            moneyMatters.moneyMattersTableData();

            Academics academics = new Academics(driver, wait);
            academics.academicsTableData();

            Students students = new Students(driver, wait);
            students.studentsTableData();

        } catch (Exception e) {e.printStackTrace();}
        i++;
    }


        driver.quit();







    }
}
