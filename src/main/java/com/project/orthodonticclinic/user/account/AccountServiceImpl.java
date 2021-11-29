package com.project.orthodonticclinic.user.account;

import com.project.orthodonticclinic.exception.ApplicationException;
import com.project.orthodonticclinic.exception.Error;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public Account createAccount(String username, String password, RoleName roleName) {
        checkIfUsernameIsUnique(username);
        var account = new Account();
        account.setUsername(username);
        account.setPassword(passwordEncoder.encode(password));
        account.setRole(getRoleEntityByRoleName(roleName));
        accountRepository.save(account);
        return account;
    }

    private void checkIfUsernameIsUnique(String username) {
        if (accountRepository.existsAccountByUsername(username)) {
            throw new ApplicationException(Error.USERNAME_IS_NOT_UNIQUE);
        }
    }

    private Role getRoleEntityByRoleName(RoleName roleName) {
        return roleRepository.findByName(roleName)
                .orElseThrow(() -> new ApplicationException(Error.INCORRECT_ROLE_NAME));
    }
}
