package Service;

import DAO.AccountDAO;
import Model.Account;

public class AccountService {
    public AccountDAO accountDAO;
    
    /**
     * Constructor for creating a new AccountService with a new AccountDAO.
     */
    public AccountService() {
        accountDAO = new AccountDAO();
    }

    /**
     * Registers a new account.
     * @param acc the account to be created.
     * @return new account if registration was successful.
     */
    public Account register(Account acc) {
        if (acc.getPassword().length() >= 4 && 
            acc.getUsername() != "" && 
            accountDAO.getAccountByUsername(acc.getUsername()) == null) {
                return accountDAO.createAccount(acc);
        }
        return null; // Credentials invalid
    }

    /**
     * Logs into account.
     * @param acc the account to be logged into.
     * @return account if login was successful.
     */
    public Account login(Account acc) {
        return accountDAO.loginAccount(acc);
    }

    /**
     * Retrieves account by ID.
     * @param id the ID of the account.
     * @return account if id was found.
     */
    public Account getAccountById(int id) {
        return accountDAO.getAccountById(id);
    }
}
