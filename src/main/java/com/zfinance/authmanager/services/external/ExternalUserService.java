package com.zfinance.authmanager.services.external;

import com.zfinance.authmanager.dto.requests.registration.RegisterUser;
import com.zfinance.authmanager.dto.response.user.UserRecord;

public interface ExternalUserService {

	public void verifyUserProfileEmail(String userId);

	public UserRecord registerUser(RegisterUser userCreateBody);

}
