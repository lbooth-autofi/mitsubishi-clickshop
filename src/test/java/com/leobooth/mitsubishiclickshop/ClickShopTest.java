package com.leobooth.mitsubishiclickshop;

import com.leobooth.BaseTest;
import com.leobooth.mitsubishiclickshop.pages.SearchResultsPage;
import com.leobooth.utils.CSVToDealerZipList;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import java.io.IOException;
import java.util.ArrayList;

public class ClickShopTest extends BaseTest {

    String csvFilePath = "/Users/lbooth/Desktop/DealershipsAndZIPCodes-20240425.csv";

    // if test run fails before checking all dealers, reset index to last failed dealer and restart test
    int dealerIndex = 0;
    int expectedDistanceFromZipCode = 1;

    private SearchResultsPage navToSearchResultsPage(WebDriver driver, SoftAssert softAssert, CSVToDealerZipList.DealerZip expectedDealer) {
        String urlVDPByZipCode = ClickShopUrl.getClickShopUatUrl(expectedDealer.getZipCode());

        SearchResultsPage searchResultsPage = new SearchResultsPage(driver, urlVDPByZipCode);
        searchResultsPage.navToPage();

        searchResultsPage.waitUntilAllPageElementsVisible(15);
        String actualPageTitle = searchResultsPage.getPageTitle();

        String expectedPageTitle = "Shop new Mitsubishi cars near you and pre-qualify | Mitsubishi powered by Drive";
        Assert.assertEquals(actualPageTitle, expectedPageTitle, "ClickShop home page title is not visible.");

        return searchResultsPage;
    }

    private void assertDealerNameAndDistance(SearchResultsPage srp, SoftAssert softAssert, int index, int tileNumber, CSVToDealerZipList.DealerZip expectedDealer, int expectedDistance) {
        String actualDealerName = "";
        String actualDistanceAsString = "";
        int actualDistance = -1;

        try {
            srp.waitUntilDealerNameVisible(15);
            actualDealerName = srp.getSRPTileDealerName(tileNumber);
            actualDistanceAsString = srp.getSRPTileDistance(tileNumber);
            actualDistance = Integer.parseInt(
                    actualDistanceAsString.substring(0, actualDistanceAsString.indexOf("mi")).trim()
            );
        } catch (TimeoutException e) {

            try {
                if (srp.isNoVehiclesFoundVisible()) {
                    System.out.println("No vehicles found for dealer: " + expectedDealer.getDealership());
                } else {
                    System.out.println("Unable to find dealer name: " + expectedDealer.getDealership());
                }
            } catch (NoSuchElementException nse) {
                softAssert.assertAll();
            }
        }

        System.out.println("index: " + index + ", expected dealer: " + expectedDealer.getDealership() + ", actual dealer: " + actualDealerName);

        softAssert.assertEquals(
                actualDealerName.toUpperCase(), expectedDealer.getDealership().toUpperCase(),
                "Unexpected result: dealer name, ZIP Code " + expectedDealer.getZipCode() + ", dealer " + expectedDealer.getDealership() + ","
        );

        softAssert.assertEquals(
                actualDistance, expectedDistance,
                "Unexpected result: distance, ZIP Code " + expectedDealer.getZipCode() + ", dealer " + actualDealerName + ","
        );
    }

    // test seems to fail if you haven't navigated to the UAT site in your regular web browser first
    // test failed at index 128; should you close browser and restart test after a certain number of dealers?
    @Test
    public void testClickShopDealers(String csvFilePath, int dealerIndex, int expectedDistanceFromZipCode) {
        WebDriver driver = setupTestDriver(TestBrowser.CHROME);
        driver.manage().window().maximize();
        SoftAssert softAssert = new SoftAssert();

        ArrayList<CSVToDealerZipList.DealerZip> dealerZipList = new ArrayList<>();

        try {
            dealerZipList = CSVToDealerZipList.convertCSV(csvFilePath, "," , false);
        } catch (IOException e) {
            Assert.fail("Unable to convert CSV to list of DealerZip objects.");
        }

        for (int index = dealerIndex; index < dealerZipList.size(); index++) {
            SearchResultsPage srp = navToSearchResultsPage(driver, softAssert, dealerZipList.get(index));
            assertDealerNameAndDistance(srp, softAssert, index, 1, dealerZipList.get(index), expectedDistanceFromZipCode);
        }

        softAssert.assertAll();
    }
}

