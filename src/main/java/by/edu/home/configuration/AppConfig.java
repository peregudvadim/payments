package by.edu.home.configuration;


import by.edu.home.transfer.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class AppConfig {


    @Bean("accountDTO")
    @Scope("prototype")
    public AccountDTO getAccountDTO(){
        return new AccountDTO();
    }

    @Bean("creditCardDTO")
    @Scope("prototype")
    public CreditCardDTO getCreditCardDTO(){
        return new CreditCardDTO();
    }

    @Bean("adminDTO")
    @Scope("prototype")
    public AdminDTO getAdminDTO(){
        return new AdminDTO();
    }

    @Bean("paymentDTO")
    @Scope("prototype")
    public PaymentDTO getPaymentDTO(){
        return new PaymentDTO();
    }


}
