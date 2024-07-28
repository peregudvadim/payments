package by.edu.home.service;

import by.edu.home.dao.ClientDAO;
import by.edu.home.entity.Account;
import by.edu.home.entity.Client;
import by.edu.home.entity.CreditCard;
import by.edu.home.transfer.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;


@Service
public class ClientServiceImpl implements ClientService {

    @Autowired
    private ClientDAO clientDAO;

    @Transactional
    public boolean registration(Client client) throws Exception {
        return clientDAO.registration(client);
    }

    @Transactional
    public Client findClientByEmail(String email) {
        return clientDAO.findClientByEmail(email);
    }

    @Transactional
    public boolean createAccountForClient(int clientId) {
        return clientDAO.createAccountForClient(clientId);
    }

    @Transactional
    public int checkClientInDatabase(String phone, String password) {
        return clientDAO.checkClientInDatabase(phone, password);
    }

    @Transactional
    public AccountDTO getAccountByID(int accountId) {
        return clientDAO.getAccountByID(accountId);
    }

    @Transactional
    public boolean blockAccount(int accountId) {
        return clientDAO.blockAccount(accountId);
    }

    @Transactional
    public boolean addMoney(int accountId, BigDecimal amount) {
        return clientDAO.addMoney(accountId, amount);
    }

    @Transactional
    public List<CreditCardDTO> getCreditCardsByAccountId(int accountId) {
        return clientDAO.getCreditCardsByAccountId(accountId);
    }

    @Transactional
    public CreditCard getCreditCardById(int creditCardId) {
        return clientDAO.getCreditCardById(creditCardId);
    }

    @Transactional
    public boolean addNewCreditCard(int accountId) {
        return clientDAO.addNewCreditCard(accountId);
    }

    @Transactional
    public boolean paymentByCard(int creditCardId, BigDecimal amount) {
        return clientDAO.paymentByCard(creditCardId, amount);
    }

    @Transactional
    public boolean blockCard(int cardId) {
        return clientDAO.blockCard(cardId);
    }

    @Transactional
    public boolean addMoneyToCard(int creditCardId, BigDecimal amount) {
        return clientDAO.addMoneyToCard(creditCardId, amount);
    }

    @Transactional
    public Client findClientByPhone(String phone) {
        return clientDAO.findClientByPhone(phone);
    }

    @Transactional
    public int checkAdmin(String phone, String password) {
        return clientDAO.checkAdmin(phone, password);
    }

    @Transactional
    public AdminDTO getAdminByID(int adminId) {
        return clientDAO.getAdminByID(adminId);
    }

    @Transactional
    public Account findAccountByClientPhone(String phone) {
        return clientDAO.findAccountByClientPhone(phone);
    }

    @Transactional
    public boolean changeAccountStatus(int accountId, String status) {
        return clientDAO.changeAccountStatus(accountId, status);
    }

    @Transactional
    public boolean changeCardStatus(int cardId, String status) {
        return clientDAO.changeCardStatus(cardId, status);
    }

    @Transactional
    public List<PaymentDTO> getPayments(Account userAccount) {
        return clientDAO.getPayments(userAccount);
    }


}




