# mitsubishi-clickshop
A basic test automation framework used to test Mitsubishi ClickShop expectations

Framework
---------
* Language: Java
* Build tool: Maven
* Test Automation Library: Selenium 4
* Test Runner: TestNG

ClickShopTest
-------------
* GIVEN a list of ZIP Codes and the nearest Mitsubishi dealer for each ZIP code
*   AND the dealer location is expected to be within 1 mile of the ZIP Code center
*  WHEN I visit a ClickShop URL containing a ZIP code
*   AND I check the first tile of the Search Results Page
*  THEN the vehicle tile should contain the name of the nearest dealer
*   AND the dealer location should be 1 mile or less from the ZIP Code center

This test compares a list of Mitsubishi ClickShop dealer names and ZIP codes 
to see if the expected dealer appears for each ZIP code, as outlined in this Jira ticket:
* https://autofi.atlassian.net/browse/TSTA-638

Test data is based on the dealer name and ZIP code columns in the "URL Sheet"
of the Macro Working Doc spreadsheet at this URL:
* https://docs.google.com/spreadsheets/d/1tq-Km7pX96b3CfSAhovcRMXTvqT9I3j7CAMN5dGT1Hs/edit?pli=1#gid=1319199383

To run ClickShopTest:
---------------------
1. Install Java and Maven
1. Install Aqua
1. Clone the mitsubishi-clickshop repository to your local machine
1. Open this repo in Aqua
1. Update the test data (a CSV of dealers and the ZIP Code for which the dealer should appear)
1. Open the ClickShopTest class
1. Update the filepath to the test data (if necessary)
1. Run the "testClickShopDealers()" test

During the test run, the current CSV row (index), zip code, expected dealer, and actual dealer should appear in the Java Console, such as:
1. "index: 315, ZIP Code: 95117, expected dealer: SAN JOSE MITSUBISHI, actual dealer: SAN JOSE MITSUBISHI"

Both the Mitsubishi ClickShop UAT and Production websites attempt to block automated tests.
If the test encounters an unexpected error and/or will only test a few ZIP codes, 
your IP address may have been blocked. To get a new IP address quickly and resume the test,
disconnect and reconnect from the AutoFi VPN.

If a test run stops unexpectedly:
1. copy the failed assertions from the Java Console to a text file or other temporary location
1. disconnect and reconnect from the AutoFi VPN
1. change the "dealerIndex" variable to the last index the test ran
1. re-run the test
1. repeat the above steps until all the dealers have been tested