package com.tranxactive.j2pay.gateways.impl.nmi;

public class Constants {

	public static final String API_URL = "https://secure.networkmerchants.com/api/transact.php";
	public final class RequestParameters {
		public static final String USERNAME = "username";
		public static final String PASSWORD = "password";
		public static final String CUSTOMER_VAULT = "customer_vault";
		public static final String CUSTOMER_VAULT_ID = "customer_vault_id";
		public static final String TRANSACTION_TYPE = "type";
		public static final String TRANSACTION_TYPE_PURCHASE = "sale";
		public static final String TRANSACTION_TYPE_REFUND = "refund";
		public static final String TRANSACTION_TYPE_VOID = "void";
		public static final String AMOUNT = "amount";
		public static final String CREDIT_CARD_NUMBER = "ccnumber";
		public static final String CREDIT_CARD_CVV = "cvv";
		public static final String CREDIT_CARD_EXPIRY_DATE = "ccexp";
		public static final String CURRENCY = "currency";
		public static final String IP_ADDRESS = "ipaddress";
		public static final String FIRSTNAME = "first_name";
		public static final String LASTNAME = "last_name";
		public static final String ADDRESS = "address1";
		public static final String CITY = "city";
		public static final String STATE = "state";
		public static final String ZIPCODE = "zip";
		public static final String COUNTRY = "country";
		public static final String PHONE_NUMBER = "phone";
		public static final String EMAIL = "email";
		public static final String ADD_CUSTOMER = "add_customer";
	}
	public final class ResponseParameters {
		public static final String TRANSACTION_ID = "transactionid";
		public static final String TEXT = "text";
		public static final String RESPONSE_TEXT = "responsetext";
		public static final String RESPONSE_CODE = "ResponseCode";
	}
}