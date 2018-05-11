package com.tranxactive.j2pay.gateways.impl.payflow;

public class Constants {

	public static final String RESULT = "RESULT";

	public static final String TEST_URL = "https://pilot-payflowpro.paypal.com";
	public static final String LIVE_URL = "https://payflowpro.paypal.com";

	public final class ResponseParameters {
		public static final String MESSAGE = "RESPMSG";
		public static final String PNREF = "PNREF";
	}
	public final class RequestParameters {
		public static final String USER = "USER";
		public static final String VENDOR = "VENDOR";
		public static final String PASSWORD = "PWD";
		public static final String PARTNER = "PARTNER";
		public static final String TRANSACTION_TYPE = "TRXTYPE";
		public static final String TRANSACTION_TYPE_PURCHASE = "S";
		public static final String TRANSACTION_TYPE_REBILL = "S";
		public static final String TRANSACTION_TYPE_VOID = "V";
		public static final String TRANSACTION_TYPE_REFUND = "C";
		public static final String TENDER = "TENDER";
		public static final String TENDER_VALUE = "C";
		public static final String ACCOUNT = "ACCT";
		public static final String CVV2 = "CVV2";
		public static final String EXPIRY_DATE = "EXPDATE";
		public static final String AMOUNT = "AMT";
		public static final String BILLING_FIRSTNAME = "BILLTOFIRSTNAME";
		public static final String BILLING_LASTNAME = "BILLTOLASTNAME";
		public static final String BILLING_STREET = "BILLTOSTREET";
		public static final String BILLING_CITY = "BILLTOCITY";
		public static final String BILLING_STATE = "BILLTOSTATE";
		public static final String BILLING_ZIPCODE = "BILLTOZIP";
		public static final String BILLING_COUNTRY = "BILLTOCOUNTRY";
		public static final String CUSTOMER_IP = "CUSTIP";
		public static final String VERBOSITY = "VERBOSITY";
		public static final String RECURRING = "RECURRING";
		public static final String PROFILE_NAME = "PROFILENAME";
		public static final String ORIGIN_ID = "ORIGID";
		public static final String ACTION = "ACTION";
	}
}