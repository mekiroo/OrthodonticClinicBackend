package com.project.orthodonticclinic.user.account;

public interface AccountService {

    Account createAccount(String username, String password, RoleName roleName);
}
