package com.leobooth.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.testng.annotations.Test;

public class EmailUtils {

  public static String generateEmail(String emailAddress) {
    String username;
    String domainAndExtension;

    username = emailAddress.substring(0,emailAddress.indexOf("@"));
    domainAndExtension = emailAddress.substring(emailAddress.indexOf("@"));

    LocalDateTime localDateTime = LocalDateTime.now();
    DateTimeFormatter dateFormatter = DateTimeFormatter.ISO_LOCAL_DATE;
    DateTimeFormatter timeFormatter = DateTimeFormatter.ISO_LOCAL_TIME;
    String dateSection = dateFormatter.format(localDateTime);
    String timeSection = timeFormatter.format(localDateTime);

    // if there is a nanosecond time portion, remove it
    if(timeSection.contains(".")) {
      timeSection = timeSection.substring(0, timeSection.indexOf("."));
    }

    // remove unwanted characters from the timestamp
    dateSection = dateSection.replace("-", "");
    timeSection = timeSection.replace(":", "");
    String timestamp = dateSection + "at" + timeSection;

    return username + "+" + timestamp + domainAndExtension;
  }

  @Test
  public void testGenerateEmail() {
    String emailAddress = "lbooth@autofi.io";
    String testEmail = generateEmail(emailAddress);
    System.out.println("test email: " + testEmail);
  }
}
