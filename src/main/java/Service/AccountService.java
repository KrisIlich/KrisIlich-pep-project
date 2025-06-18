package Service;

import DAO.AccountDAO;
import Model.Account;

public class AccountService {
    
    private AccountDAO accountDAO;

    public AccountService(){
        accountDAO = new AccountDAO();
    }

    public Account registerAccount(Account account) {
        if (account.getUsername() == null || account.getUsername().trim().isEmpty()) {
            return null;
        } else if (account.getPassword().length() < 4) {
            return null;
        } else {
        return accountDAO.registerAccount(account);
        }
    }

    public Account accountLogin(Account account){
        return accountDAO.accountLogin(account);
    }

    

}
