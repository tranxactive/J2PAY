package com.tranxactive.j2pay.gateways.util;

import java.util.Random;

import static java.lang.System.currentTimeMillis;

/**
 *
 * @author dwamara
 */
public class UniqueCustomerIdGenerator {
    public static String getUniqueCustomerId() {
        String str = "123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder uniqueString = new StringBuilder(String.valueOf(currentTimeMillis()));

        Random random = new Random();

        while (uniqueString.length() < 20) {
            uniqueString.append(str.charAt(random.nextInt(str.length() - 1)));
        }

        return uniqueString.toString();
    }
    public static String getUniqueVaultId() {
        return getUniqueCustomerId();
    }

}
