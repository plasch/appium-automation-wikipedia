package lib.ui.ios;

import io.appium.java_client.AppiumDriver;
import lib.ui.ArticlePageObject;

public class iOSArticlePageObject extends ArticlePageObject
{
    static {
        TITLE = "id:Java (programming language)";
        FOOTER_ELEMENT = "id:View article in browser";
        OPTION_ADD_TO_MY_LIST_BUTTON = "id:Save for later";
        CLOSE_ARTICLE_BUTTON = "id:Back";
        TITLE_TPL = "id:{SEARCH_TEXT}";
        // CLOSE_POP_UP_BUTTON = "xpath://XCUIElementTypeButton[@name='places auth close']";
        CLOSE_POP_UP_BUTTON = "id:places auth close";
        CREATED_FOLDER_NAME_TPL = "xpath://*[@text='{NAME_OF_EXIST_FOLDER}']";
    }

    public iOSArticlePageObject(AppiumDriver driver)
    {
        super(driver);
    }
}
