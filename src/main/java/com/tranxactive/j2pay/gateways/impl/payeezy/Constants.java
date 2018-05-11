package com.tranxactive.j2pay.gateways.impl.payeezy;

public class Constants {
	public static final String TRANSACTION_RESULT = "TransactionResult";
	public static final String TEST_URL = "https://api.demo.globalgatewaye4.firstdata.com/transaction/v11";
	public static final String LIVE_URL = "https://api.globalgatewaye4.firstdata.com/transaction/v11";

	public final class RequestParameters {
		public static final String TRANSACTION_KEY = "transactionKey";
	}

	public final class ResponseParameters {
		public static final String TRANSACTION_ERROR = "Transaction_Error";
		public static final String TRANSACTION_APPROVED = "Transaction_Approved";
		public static final String BANK_MESSAGE = "Bank_Message";
		public static final String TRANSACTION_TAG = "Transaction_Tag";
		public static final String AUTHORIZATION_NUMBER = "Authorization_Num";
		public static final String J2PAY_AUTHORIZATION_NUMBER = "authorizationNumber";
		public static final String EXACT_MESSAGE = "EXact_Message";
		public static final String EXACT_ID = "exactId";
		public static final String PASSWORD = "password";
		public static final String NAME = "name";
	}
}