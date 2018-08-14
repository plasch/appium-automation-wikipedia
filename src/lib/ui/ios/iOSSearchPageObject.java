package lib.ui.ios;

import io.appium.java_client.AppiumDriver;
import lib.ui.SearchPageObject;

public class iOSSearchPageObject extends SearchPageObject
{
    static {
    SEARCH_INIT_ELEMENT = "xpath://XCUIElementTypeSearchField[@name='Search Wikipedia']";
//    SEARCH_INPUT = "xpath://XCUIElementTypeSearchField[@value='Search Wikipedia']";
    SEARCH_INPUT = "xpath://XCUIElementTypeApplication[@name='Wikipedia']/XCUIElementTypeWindow[1]/XCUIElementTypeOther[2]/XCUIElementTypeOther/XCUIElementTypeOther[1]/XCUIElementTypeSearchField";
    SEARCH_CANCEL_BUTTON = "id:Close";
    SEARCH_RESULT_BY_SUBSTRING_TPL = "xpath://XCUIElementTypeLink[contains(@name, '{SUBSTRING}')]";
    SEARCH_RESULT_ELEMENT = "xpath://XCUIElementTypeLink";
    SEARCH_EMPTY_RESULT_ELEMENT = "xpath://XCUIElementTypeStaticText[@name='No results found']";

    SEARCH_RESULTS_EMPTY_MESSAGE = "id:org.wikipedia:id/search_empty_message";
    SEARCH_TITLE_AND_DESCRIPTION_TPL = "xpath://*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@resource-id='org.wikipedia:id/page_list_item_title'][contains(@text,'{TITLE}')]/../*[@resource-id='org.wikipedia:id/page_list_item_description'][@text='{DESCRIPTION}']/..";
    }

    public iOSSearchPageObject(AppiumDriver driver)
    {
        super(driver);
    }
}
