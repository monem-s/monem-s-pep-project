package Service;

import DAO.AccountDAO;
import Model.Account;

public class AccountService {
    public AccountDAO accountDAO;
    
    public AccountService() {
        accountDAO = new AccountDAO();
    }

    public Account register(Account acc) {
        if (acc.getPassword().length() >= 4 && 
            acc.getUsername() != "" && 
            accountDAO.getAccountByUsername(acc.getUsername()) == null) {
                return accountDAO.createAccount(acc);
        }
        return null;
    }

    public Account login(Account acc) {
        return accountDAO.loginAccount(acc);
    }

    public Account getAccountById(int id) {
        return accountDAO.getAccountById(id);
    }
}
