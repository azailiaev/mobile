package lib.ui;

import io.appium.java_client.AppiumDriver;
import lib.Platform;
import org.openqa.selenium.remote.RemoteWebDriver;

abstract public class SearchPageObject extends MainPageObject{

    protected static String
            SKIP_BUTTON,
            SEARCH_INIT_ELEMENT,
            SEARCH_INPUT,
            SEARCH_CANCEL_BUTTON,
            SEARCH_RESULT_BY_SUBSTRING_TPL,
            SEARCH_RESULT_BY_TITLE_AND_DESCRIPTION_SUBSTRINGS_TPL,
            SEARCH_RESULT_ELEMENT,
            SEARCH_EMPTY_RESULT_ELEMENT,
            SEARCH_ICON,
            RECENTLY_SEARCHED;

    public SearchPageObject(RemoteWebDriver driver)
    {
        super(driver);
    }

    /* TEMPLATES METHODS */
    private static String getResultSearchElement(String substring)
    {
        return SEARCH_RESULT_BY_SUBSTRING_TPL.replace("{SUBSTRING}", substring);
    }

    private static String getResultSearchElementByTitleAndDescription(String title, String description)
    {
        return SEARCH_RESULT_BY_TITLE_AND_DESCRIPTION_SUBSTRINGS_TPL.replace("{TITLE}", title).replace("{DESCRIPTION}", description);
    }
    /* TEMPLATES METHODS */

    public void clickSkipButton()
    {
        this.waitForElementAndClick(SKIP_BUTTON, "Cannot find and click SKIP button", 15);
    }
    public void initSearchInput()
    {
        this.waitForElementPresent(SEARCH_INIT_ELEMENT, "Cannot find search input after clicking search init element", 5);
        this.waitForElementAndClick(SEARCH_INIT_ELEMENT, "Cannot find and click search init element", 5);
    }

    public void searchIcon()
    {
        this.waitForElementAndClick(SEARCH_ICON, "Cannot find Search icon", 10);
    }

    public void waitForCancelButtonToAppear()
    {
        this.waitForElementPresent(SEARCH_CANCEL_BUTTON, "Cannot find search cancel button", 5);
    }

    public void waitForCancelButtonToDisappear()
    {
        this.waitForElementNotPresent(SEARCH_CANCEL_BUTTON, "Search cancel button still present", 5);
    }

    public void clickCancelSearch()
    {
        this.waitForElementAndClick(SEARCH_CANCEL_BUTTON, "Cannot find and click search cancel button", 5);
    }

    public void clearSearchField()
    {
        if (Platform.getInstance().isAndroid()){
            this.waitForElementAndClear(SEARCH_INPUT, "Cannot clear search field", 5);
        } else {
            this.waitForElementAndClear(SEARCH_INIT_ELEMENT, "Cannot clear search field", 5);
        }
    }

    public void typeSearchLine(String serch_line)
    {
        this.waitForElementAndSendKeys(SEARCH_INPUT, serch_line, "Cannot find and type into search input", 5);
    }

    public void waitForSearchResult(String substring)
    {
        String search_result_xpath = getResultSearchElement(substring);
        this.waitForElementPresent(search_result_xpath, "Cannot find search result with substring " + substring, 5);
    }

    public void waitForElementByTitleAndDescription(String title, String description)
    {
        String search_result_xpath = getResultSearchElementByTitleAndDescription(title, description);
        this.waitForElementPresent(search_result_xpath, "Cannot find search result with title " + title + " and description " + description, 5);
    }

    public void clickByArticleWithSubstring(String substring)
    {
        String search_result_xpath = getResultSearchElement(substring);
        this.waitForElementAndClick(search_result_xpath, "Cannot find and click search result with substring " + substring, 10);
    }

    public int getAmountOfFoundArticles()
    {
        this.waitForElementPresent(
                SEARCH_RESULT_ELEMENT,
                "Cannot find anything by request",
                15
        );
        return this.getAmountOfElements(SEARCH_RESULT_ELEMENT);
    }

    public void waitForEmptyResultsLabel()
    {
        this.waitForElementPresent(SEARCH_EMPTY_RESULT_ELEMENT, "Cannot find empty result element", 15);
    }

    public void assertThereIsNoResultOfSearch()
    {
        this.assertElementNotPresent(SEARCH_RESULT_ELEMENT, "We supposed not to find any results");
    }

    public void assertAtLeastThreeElements(String locator, int i)
    {
        int elements_amount = getAmountOfElements(locator);
        if (elements_amount <= i)
        {
            throw new AssertionError("Too few results");
        }
    }

    public void assertAtLeastThreeArticles()
    {
        this.waitForElementPresent("//*[@resource-id='org.wikipedia:id/search_results_list']//*[@resource-id='org.wikipedia:id/page_list_item_title']", "No one element was not found", 5);
        this.assertAtLeastThreeElements("//*[@resource-id='org.wikipedia:id/search_results_list']//*[@resource-id='org.wikipedia:id/page_list_item_title']", 3);
    }
}
