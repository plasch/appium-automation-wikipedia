package lib.ui.factories;

import io.appium.java_client.AppiumDriver;
import lib.Platform;
import lib.ui.MyListsPageObject;
import lib.ui.android.AndroidMyListsPageObject;
import lib.ui.ios.iOSMyListsPageObject;

public class MyListsPageObjectFactory
{
    public static MyListsPageObject get(AppiumDriver driver)
    {
        if (Platform.getInstance().isAndroid()) {
            return new AndroidMyListsPageObject(driver);
        } else {
            return new iOSMyListsPageObject(driver);
        }
    }
}
