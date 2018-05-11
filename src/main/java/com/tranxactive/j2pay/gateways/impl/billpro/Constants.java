package com.tranxactive.j2pay.gateways.impl.billpro;

public class Constants {
	public static final String URL = "https://gateway.billpro.com";

	public final class RequestParameters {
		public static final String REFERENCE = "Reference";
		public static final String AMOUNT = "amount";
		public static final String CURRENCY = "currency";
		public static final String EMAIL_ADDRESS = "emailAddress";
		public static final String IP_ADDRESS = "ipAddress";
		public static final String PHONE_NUMBER = "phoneNumber";
		public static final String FIRSTNAME = "firstName";
		public static final String LASTNAME = "lastName";
		public static final String ADDRESS = "address";
		public static final String CITY = "city";
		public static final String STATE = "state";
		public static final String ZIPCODE = "zipcode";
		public static final String COUNTRY = "country";
		public static final String CREDIT_CARD_NUMBER = "ccnumber";
		public static final String CREDIT_CARD_EXPIRY_MONTH = "expiryMonth";
		public static final String CREDIT_CARD_EXPIRY_YEAR = "expiryYear";
		public static final String CREDIT_CARD_CVV = "cvv";
	}

	public final class ResponseParameters {
		public static final String RESPONSE = "Response";
		public static final String RESPONSE_CODE = "ResponseCode";
		public static final String TRANSACTION_ID = "TransactionID";
		public static final String DESCRIPTION = "Description";
		public static final String ACCOUNT_ID = "AccountID";
		public static final String ACCOUNT_AUTHORIZATION = "AccountAuth";
	}
}