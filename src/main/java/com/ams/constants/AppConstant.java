package com.ams.constants;

public interface AppConstant {

	String TXN_PREFIX = "9";
	
	interface PROCESSING{
		String CREATE_TXN_BANK = "CREATE_TXN-BANK";
		String CREATE_TXN_MRCH = "CREATE_TXN-MRCH";
		String POSTED_TXN_BANK = "POSTED_TXN-BANK";
		String POSTED_TXN_MRCH = "POSTED_TXN-MRCH";
		String BATCH = "BATCH";
		String HOSTED = "HOSTED";
	}
	interface APP_CONFIG_CONSTANTS{
		String MERCHANT_SIGNUP = "MERCHANT_SIGNUP";
		String ACTION_CODE = "ACTION_CODE";
	}
	interface EMAIL_TEMPLATES{
		String ISSUE_CREATION_EMAIL_TEMPLATE = "issueCreationEmail";
	}
	interface QUERY_CONSTANTS{
		String getAllFindingCount = "SELECT status, count(*) AS count FROM VW_FINDING_COUNT group by status";
	}
}