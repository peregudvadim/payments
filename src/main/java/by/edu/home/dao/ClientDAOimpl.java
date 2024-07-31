package by.edu.home.dao;

import by.edu.home.constant.Status;
import by.edu.home.entity.*;
import by.edu.home.transfer.*;
import org.hibernate.query.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.mindrot.jbcrypt.BCrypt;

import java.math.BigDecimal;
import java.security.SecureRandom;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;


@Repository
public class ClientDAOimpl implements ClientDAO {

    @Autowired
    private SessionFactory sessionFactory;


    @Override
    public boolean registration(Client client) {
        Session currentSession = sessionFactory.getCurrentSession();

        Client isExistClient = findClientByEmail(client.getEmail());
        if (isExistClient != null) {
            throw new IllegalArgumentException("A client with this email already exists");
        } else {
            isExistClient = findClientByPhone(client.getPhone());
            if (isExistClient != null) {
                throw new IllegalArgumentException("A client with this phone number already exists");
            }
        }

        String password = client.getPassword();
        String salt = BCrypt.gensalt();
        String hashedPassword = BCrypt.hashpw(password, salt);
        client.setPassword(hashedPassword);


        currentSession.save(client);
        Integer clientId = client.getClientId();

        if (clientId != null) {
            return createAccountForClient(clientId);
        } else {
            return false;
        }

    }

    @Override
    public Client findClientByEmail(String email) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "FROM Client c WHERE c.email = :email";
        Query<Client> query = session.createQuery(hql, Client.class);
        if (email != null) {
            query.setParameter("email", email);
        } else {
            return null;
        }
        return query.uniqueResult();
    }

    @Override
    public Client findClientByPhone(String phone) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "FROM Client c WHERE c.phone = :phone";
        Query<Client> query = session.createQuery(hql, Client.class);
        if (phone != null) {
            query.setParameter("phone", phone);
        } else {
            return null;
        }
        return query.uniqueResult();
    }

    @Override
    public Account findAccountByClientPhone(String phone) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "SELECT a FROM Account a JOIN a.client c WHERE c.phone = :phone";
        Query<Account> query = session.createQuery(hql, Account.class);
        if (phone != null) {
            query.setParameter("phone", phone);
        } else {
            return null;
        }
        return query.uniqueResult();
    }


    @Override
    public int checkAdmin(String phone, String password) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "SELECT a FROM Administrator a WHERE a.phone = :phone";
        Query<Administrator> query = session.createQuery(hql, Administrator.class);

        if (phone != null && password != null) {
            query.setParameter("phone", phone);
            Administrator admin = query.uniqueResult();
            if (admin != null) {
                String hashedPassword = admin.getPassword();
                if (BCrypt.checkpw(password, hashedPassword)) {
                    return admin.getAdminId();
                }
            }
        }
        return 0;
    }

    @Override
    public AdminDTO getAdminByID(int adminId) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "FROM Administrator WHERE id = :adminId";
        Query<Administrator> query = session.createQuery(hql, Administrator.class);
        query.setParameter("adminId", adminId);
        AdminDTO adminDTO = convertToDTO(query.uniqueResult());

        return adminDTO;

    }

    @Override
    public boolean createAccountForClient(int clientId) {
        Session session = sessionFactory.getCurrentSession();
        Client client = session.get(Client.class, clientId);
        if (client == null) {
            throw new IllegalArgumentException("User not found");
        }

        Account account = new Account();
        account.setClient(client);
        account.setCreatedAt(Timestamp.from(Instant.now()));
        account.setStatus(Status.ACTIVE);
        while (isAccountNumberExists(account.getAccountNumber())) {
            account.setAccountNumber(account.generateAccountNumber());
        }

        int accountId = (int) session.save(account);
        return accountId != 0;
    }

    private boolean isAccountNumberExists(String accountNumber) {
        Session session = sessionFactory.getCurrentSession();
        String query = "SELECT 1 FROM Account WHERE accountNumber = :accountNumber";
        return session.createQuery(query)
                .setParameter("accountNumber", accountNumber)
                .uniqueResult() != null;
    }


    public int checkClientInDatabase(String phone, String password) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "SELECT c FROM Client c WHERE c.phone = :phone";

        Query<Client> query = session.createQuery(hql, Client.class);
        query.setParameter("phone", phone);

        Client client = query.uniqueResult();

        if (client == null) {
            throw new IllegalArgumentException("User not found");
        } else {
            String hashedPassword = client.getPassword();

            if (BCrypt.checkpw(password, hashedPassword)) {
                return client.getAccount().getAccountId();
            } else {
                throw new IllegalArgumentException("Invalid password");
            }
        }
    }

    @Override
    public AccountDTO getAccountByID(int accountId) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "FROM Account WHERE id = :accountId";
        Query<Account> query = session.createQuery(hql, Account.class);
        query.setParameter("accountId", accountId);
        AccountDTO accountDTO = convertToDTO(query.uniqueResult());

        return accountDTO;
    }

    @Override
    public boolean blockAccount(int accountId) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "FROM Account WHERE id = :accountId";
        Query<Account> query = session.createQuery(hql, Account.class);
        query.setParameter("accountId", accountId);
        Account account = query.uniqueResult();
        if (account != null) {
            account.setStatus(Status.BLOCKED);
            session.update(account);

            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean changeAccountStatus(int accountId, String status) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "FROM Account WHERE id = :accountId";
        Query<Account> query = session.createQuery(hql, Account.class);
        query.setParameter("accountId", accountId);
        Account account = query.uniqueResult();
        if (account != null) {
            Status newStatus = Status.valueOf(status.toUpperCase());
            if (newStatus.equals(Status.ACTIVE)) {
                account.setStatus(Status.ACTIVE);
                session.update(account);
            }
            if (newStatus.equals(Status.BLOCKED)) {
                account.setStatus(Status.BLOCKED);
                session.update(account);
            }
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean changeCardStatus(int cardId, String status) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "FROM CreditCard WHERE id = :cardId";
        Query<CreditCard> query = session.createQuery(hql, CreditCard.class);
        query.setParameter("cardId", cardId);
        CreditCard creditCard = query.uniqueResult();
        if (creditCard != null) {
            Status newStatus = Status.valueOf(status.toUpperCase());
            if (newStatus.equals(Status.ACTIVE)) {
                creditCard.setStatus(Status.ACTIVE);
                session.update(creditCard);
            }
            if (newStatus.equals(Status.BLOCKED)) {
                creditCard.setStatus(Status.BLOCKED);
                session.update(creditCard);
            }
            return true;
        } else {
            return false;
        }
    }

    @Override
    public List<PaymentDTO> getPayments(Account userAccount) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "FROM Payment p WHERE p.account = :account";
        Query<Payment> query = session.createQuery(hql, Payment.class);
        query.setParameter("account", userAccount);

        List<Payment> paymentList = query.list();
        if (paymentList != null) {
            return paymentList.stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
        } else {
            return Collections.emptyList();
        }
    }


    @Override
    public boolean addMoney(int accountId, BigDecimal amount) {
        Session session = sessionFactory.getCurrentSession();
        Account account = session.get(Account.class, accountId);
        BigDecimal initialBalance = account.getBalance();
        if (account != null) {
            account.setBalance(account.getBalance().add(amount));
            session.update(account);
        }
        BigDecimal finalBalance = account.getBalance();
        BigDecimal difference = finalBalance.subtract(initialBalance);
        return difference.compareTo(amount) == 0;
    }

    @Override
    public List<CreditCardDTO> getCreditCardsByAccountId(int accountId) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "FROM Account WHERE id = :accountId";
        Query<Account> query = session.createQuery(hql, Account.class);
        query.setParameter("accountId", accountId);
        Account account = query.uniqueResult();

        if (account != null) {
            return account.getCreditCards().stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
        } else {
            return Collections.emptyList();
        }


    }

    @Override
    public CreditCard getCreditCardById(int creditCardId) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "FROM CreditCard WHERE id = :creditCardId";
        Query<CreditCard> query = session.createQuery(hql, CreditCard.class);
        query.setParameter("creditCardId", creditCardId);
        CreditCard creditCard = query.uniqueResult();
        return creditCard;
    }

    @Override
    public boolean addNewCreditCard(int accountId) {
        Session session = sessionFactory.getCurrentSession();
        CreditCard creditCard = createNewCreditCard(accountId);

        int creditCardId = (int) session.save(creditCard);
        return creditCardId != 0;


    }

    @Override
    public boolean paymentByCard(int creditCardId, BigDecimal amount) {
        Session session = sessionFactory.getCurrentSession();

        CreditCard creditCard = session.get(CreditCard.class, creditCardId);
        if (creditCard == null) {
            return false;
        }

        Account account = creditCard.getAccount();

        BigDecimal newBalance = creditCard.getBalance().subtract(amount);
        if (newBalance.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Not enough money on the card");
        }

        creditCard.setBalance(creditCard.getBalance().subtract(amount));
        session.update(creditCard);
        Payment payment = new Payment();
        payment.setAccount(account);
        payment.setAmount(amount);
        payment.setPaymentDate(new Timestamp(System.currentTimeMillis()));
        session.save(payment);

        return true;
    }

    @Override
    public boolean addMoneyToCard(int creditCardId, BigDecimal amount) {
        Session session = sessionFactory.getCurrentSession();
        CreditCard creditCard = session.get(CreditCard.class, creditCardId);
        Account account = creditCard.getAccount();
        // Получаем карту по ID
        if (creditCard == null) {
            return false;
        }
        BigDecimal newBalance = account.getBalance().subtract(amount);
        if (newBalance.compareTo(BigDecimal.ZERO) < 0) {
            return false;
        }
        account.setBalance(account.getBalance().subtract(amount));
        creditCard.setBalance(creditCard.getBalance().add(amount));
        session.update(account);
        session.update(creditCard);
        return true;
    }

    @Override
    public boolean blockCard(int cardId) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "FROM CreditCard WHERE id = :cardId";
        Query<CreditCard> query = session.createQuery(hql, CreditCard.class);
        query.setParameter("cardId", cardId);
        CreditCard creditCard = query.uniqueResult();
        if (creditCard != null) {
            creditCard.setStatus(Status.BLOCKED);
            session.update(creditCard);
            return true;
        } else {
            return false;
        }
    }


    private CreditCard createNewCreditCard(int accountId) {
        SecureRandom random = new SecureRandom();
        String cardNumber = generateRandomNumber(16);
        String cvv = String.format("%03d", random.nextInt(1000));
        CreditCard creditCard = new CreditCard();
        creditCard.setCardNumber(cardNumber);
        creditCard.setCvv(cvv);
        creditCard.setBalance(BigDecimal.ZERO);
        creditCard.setStatus(Status.ACTIVE);
        Account account = new Account();
        account.setAccountId(accountId);
        creditCard.setAccount(account);

        return creditCard;
    }

    private String generateRandomNumber(int length) {
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }


    private AccountDTO convertToDTO(Account account) {
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setAccountId(account.getAccountId());
        accountDTO.setStatus(String.valueOf(account.getStatus()));
        accountDTO.setName(account.getClient().getName());
        accountDTO.setEmail(account.getClient().getEmail());
        accountDTO.setPhone(account.getClient().getPhone());
        accountDTO.setBalance(account.getBalance());
        accountDTO.setCreditCards(account.getCreditCards().stream().map(this::convertToDTO).collect(Collectors.toList()));
        return accountDTO;
    }

    private CreditCardDTO convertToDTO(CreditCard card) {
        CreditCardDTO cardDTO = new CreditCardDTO();
        cardDTO.setCardId(card.getCardId());
        cardDTO.setCardNumber(card.getCardNumber());
        cardDTO.setExpirationDate(card.getExpirationDate());
        cardDTO.setBalance(card.getBalance());
        cardDTO.setStatus(String.valueOf(card.getStatus()));
        return cardDTO;
    }

    private AdminDTO convertToDTO(Administrator admin) {
        AdminDTO adminDTO = new AdminDTO();
        adminDTO.setId(admin.getAdminId());
        adminDTO.setName(admin.getName());
        adminDTO.setRole(admin.getRole());
        return adminDTO;
    }

    private PaymentDTO convertToDTO(Payment payment) {
        PaymentDTO paymentDTO = new PaymentDTO();
        paymentDTO.setName(payment.getAccount().getClient().getName());
        paymentDTO.setEmail(payment.getAccount().getClient().getEmail());
        paymentDTO.setPhone(payment.getAccount().getClient().getPhone());
        paymentDTO.setAmount(payment.getAmount());
        paymentDTO.setPaymentDate(payment.getPaymentDate());
        return paymentDTO;
    }
}


