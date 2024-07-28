package by.edu.home.service;

import by.edu.home.entity.Account;
import by.edu.home.entity.Client;
import by.edu.home.entity.CreditCard;
import by.edu.home.transfer.*;

import java.math.BigDecimal;
import java.util.List;

public interface ClientService {


    boolean registration(Client client) throws Exception;

    Client findClientByEmail(String email);

    boolean createAccountForClient(int clientId);

    int checkClientInDatabase(String phone, String password);

    AccountDTO getAccountByID(int accountId);

    boolean blockAccount (int accountId);

    boolean addMoney(int accountId, BigDecimal amount);

    List<CreditCardDTO> getCreditCardsByAccountId(int accountId);

    CreditCard getCreditCardById(int creditCardId);

    boolean addNewCreditCard(int accountId);

    boolean paymentByCard(int creditCardId, BigDecimal amount);

    boolean blockCard(int cardId);

    boolean addMoneyToCard(int creditCardId, BigDecimal amount);

    Client findClientByPhone(String phone);

    int checkAdmin(String phone, String password);

    AdminDTO getAdminByID(int adminId);

    Account findAccountByClientPhone(String phone);

    boolean changeAccountStatus(int accountId, String status);

    boolean changeCardStatus(int cardId, String status);

    List<PaymentDTO> getPayments(Account userAccount);
}
