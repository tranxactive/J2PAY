package com.tranxactive.j2pay.gateways.impl.authorize;

public class Constants {
	public static final String LIVE_URL = "https://api.authorize.net/xml/v1/request.api";
	public static final String TEST_URL = "https://apitest.authorize.net/xml/v1/request.api";

	public final class RequestParameters {
		public static final String TRANSACTION_KEY = "transactionKey";
		public static final String CUSTOMER_PROFILE_ID = "customerProfileId";
		public static final String PAYMENT_PROFILE_ID = "paymentProfileId";
		public static final String CREDIT_CARD_NUMBER = "creditCardNumber";
		public static final String CREDIT_CARD_EXPIRATION_DATE = "creditCardExpirationDate";
		public static final String CREDIT_CARD_CVV = "creditCardCvv";
		public static final String UNIQUE_CUSTOMER_ID = "uniqueCustomerId";
		public static final String EMAIL_ADDRESS = "emailAddress";
		public static final String FIRSTNAME = "firstName";
		public static final String LASTNAME = "lastName";
		public static final String ADDRESS = "address";
		public static final String CITY = "city";
		public static final String STATE = "state";
		public static final String ZIPCODE = "zipcode";
		public static final String COUNTRY = "country";
		public static final String PHONE_NUMBER = "phoneNumber";
		public static final String IP_ADDRESS = "ipAddress";
	}
	public final class ResponseParameters {
		public static final String NAME = "name";
		public static final String CREATE_TRANSACTION_RESPONSE = "createTransactionResponse";
		public static final String TRANSACTION_RESPONSE = "transactionResponse";
		public static final String PROFILE_RESPONSE = "profileResponse";
		public static final String CUSTOMER_PAYMENT_PROFILE_ID_LIST = "customerPaymentProfileIdList";
		public static final String NUMERIC_STRING = "numericString";
		public static final String ACCOUNT_NUMBER = "accountNumber";
		public static final String RESPONSE_CODE = "responseCode";
		public static final String TRANSACTION_ID = "transId";
		public static final String ERROR_RESPONSE = "ErrorResponse";
		public static final String ERRORS = "errors";
		public static final String ERROR = "error";
		public static final String ERROR_TEXT = "errorText";
		public static final String MESSAGES = "messages";
		public static final String MESSAGE = "message";
		public static final String DESCRIPTION = "Description";
		public static final String TEXT = "text";
	}
}