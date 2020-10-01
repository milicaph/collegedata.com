package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.Utilities;

import java.util.concurrent.TimeUnit;

public class SearchPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    private
    @FindBy(css = "div.t-title__details > a[href*=college]")
    WebElement searchResult;
    private
    @FindBy(css = "span.field-validation-error")
    WebElement validationError;

    /* private
    @FindBy(id = "SearchForACollege")
    WebElement searchInput;
    private
    @FindBy(css = "button[class*=btn--search]")
    WebElement searchButton;
    private
    @FindBy(css = "ul#search_suggestions_result > li:nth-child(1)")
    WebElement inactiveItem;
    private
    @FindBy(css = "li.active")
    WebElement activeItem;
    private
    @FindBy(css = "li.active > a:nth-child(1)")
    WebElement firstSuggestion;*/

    public SearchPage(WebDriver driver, WebDriverWait wait){
        this.driver = driver;
        this.wait = wait;
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, 15), this);

    }

    public void findCollege(String collegeName, String city){
        driver.get("https://www.collegedata.com/en/explore-colleges/college-search/SearchByPreference/?SearchByPreference.SearchType=1&SearchByPreference.CollegeName="
                    + collegeName + "&SearchByPreference.City" + city);
        try{ Thread.sleep(1000); } catch (Exception ignored){}

        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector("div.container-fluid")));
        if(searchResult.isDisplayed())
            driver.get(searchResult.getAttribute("href"));
        /*searchInput.click();
        searchInput.sendKeys(collegeName);

        wait.until(ExpectedConditions.visibilityOf(inactiveItem));
        Utilities.moveToElement(driver, inactiveItem);
        System.out.println(firstSuggestion.toString());

        wait.until(ExpectedConditions.elementToBeClickable(firstSuggestion));
        firstSuggestion.click();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);*/


    }









}
