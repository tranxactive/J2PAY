package com.tranxactive.j2pay.gateways.parameters;

public class Constants {
	public static final String ENCODING_UTF8 = "UTF-8";

	public class Gateway {
		public final class Authorize {
			public static final String LIVE_URL = "https://api.authorize.net/xml/v1/request.api";
			public static final String TEST_URL = "https://apitest.authorize.net/xml/v1/request.api";

			public final class RequestParameters {
				public static final String TRANSACTION_KEY = "transactionKey";
				public static final String TRANSACTION_TYPE_PURCHASE = "authCaptureTransaction";
				public static final String TRANSACTION_TYPE_REBILL = "authCaptureTransaction";
				public static final String CUSTOMER_PROFILE_ID = "customerProfileId";
				public static final String PAYMENT_PROFILE_ID = "paymentProfileId";
				public static final String TRANSACTION_TYPE_REFUND = "refundTransaction";
				public static final String TRANSACTION_TYPE_VOID = "voidTransaction";
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

		public final class Billpro {
			public static final String URL = "https://gateway.billpro.com";

			public final class ResponseParameters {
				public static final String RESPONSE = "Response";
				public static final String RESPONSE_CODE = "ResponseCode";
				public static final String TRANSACTION_ID = "TransactionID";
				public static final String DESCRIPTION = "Description";
				public static final String ACCOUNT_ID = "AccountID";
				public static final String ACCOUNT_AUTHORIZATION = "AccountAuth";
				public static final String REFERENCE = "Reference";
			}
		}

		public final class Easypay {
			public static final String URL = "https://secure.easypaydirectgateway.com/api/transact.php";

			public final class RequestParameters {
                public static final String USERNAME = "username";
				public static final String PASSWORD = "password";
				public static final String RESPONSE_TEXT = "responsetext";
				public static final String TRANSACTION_TYPE = "type";
				public static final String TRANSACTION_TYPE_PURCHASE = "sale";
				public static final String TRANSACTION_TYPE_REFUND = "refund";
				public static final String TRANSACTION_TYPE_VOID = "void";
				public static final String AMOUNT = "amount";
				public static final String CREDIT_CARD_NUMBER = "ccnumber";
				public static final String CREDIT_CARD_CVV = "cvv";
				public static final String CREDIT_CARD_EXPIRY_DATE = "ccexp";
				public static final String CURRENCY = "currency";
				public static final String BILLING_METHOD = "billing_method";
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
				public static final String CUSTOMER_VAULT = "customer_vault";
				public static final String RECURRING = "recurring";
				public static final String ADD_CUSTOMER = "add_customer";

			}
			public final class ResponseParameters {
				public static final String RESPONSE = "Response";
				public static final String TRANSACTION_ID = "transactionid";
				public static final String CUSTOMER_VAULT_ID = "customerVaultId";
			}
		}

		public final class NMI {
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

		public final class Payeezy {
			public static final String TRANSACTION_RESULT = "TransactionResult";

			public final class ResponseParameters {
				public static final String TRANSACTION_ERROR = "Transaction_Error";
				public static final String TRANSACTION_APPROVED = "Transaction_Approved";
				public static final String BANK_MESSAGE = "Bank_Message";
				public static final String TRANSACTION_TAG = "Transaction_Tag";
				public static final String TRANSACTION_TYPE_REFUND = "34";
				public static final String TRANSACTION_TYPE_VOID = "33";
				public static final String AUTHORIZATION_NUMBER = "Authorization_Num";
				public static final String J2PAY_AUTHORIZATION_NUMBER = "authorizationNumber";
				public static final String EXACT_MESSAGE = "EXact_Message";
				public static final String EXACT_ID = "exactId";
				public static final String PASSWORD = "password";
			}
		}

		public final class PayflowPro {
			public static final String RESULT = "RESULT";
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
	}
}