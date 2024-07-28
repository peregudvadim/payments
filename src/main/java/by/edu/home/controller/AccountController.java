package by.edu.home.controller;

import by.edu.home.entity.CreditCard;
import by.edu.home.service.ClientService;
import by.edu.home.transfer.AccountDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.util.Locale;

@Controller
public class AccountController {


    private final static Logger LOGGER = LogManager.getRootLogger();

    @Autowired
    private ClientService clientService;

    @Autowired
    private LocaleResolver localeResolver;


    @RequestMapping("/blockAccount")
    public String blockAccount(HttpSession session, RedirectAttributes redirectAttributes) {
        try {
            int accountId = (int) session.getAttribute("accountId");
            String message = "";
            if (clientService.blockAccount(accountId)) {
                message = "Аккаунт заблокирован";
            } else {
                message = "Аккаунт не получилось заблокировать";
                redirectAttributes.addFlashAttribute("message", "Аккаунт не получилось заблокировать");
            }
            AccountDTO accountDTO = clientService.getAccountByID(accountId);
            redirectAttributes.addFlashAttribute("account", accountDTO);
            return "redirect:/accountPage";
        } catch (Exception e) {
            if (session != null) {
                session.invalidate();
            }
            LOGGER.error("ERROR", e.getMessage());
            return "redirect:/";
        }
    }

    @RequestMapping("/blockCard")
    public String blockCard(HttpSession session, @RequestParam("cardId") int cardId, RedirectAttributes redirectAttributes) {
        try {
            int accountId = (int) session.getAttribute("accountId");
            String message = "";
            if (clientService.blockCard(cardId)) {
                message = "Карта заблокирована";
            } else {
                message = "Карту не получилось заблокировать";
            }
            redirectAttributes.addFlashAttribute("message", message);
            return "redirect:/viewCreditCards";
        } catch (Exception e) {
            if (session != null) {
                session.invalidate();
            }
            LOGGER.error("ERROR", e.getMessage());
            return "redirect:/";
        }
    }

    @RequestMapping("/addMoneyToCard")
    public String addMoneyToCard(@RequestParam("cardId") int cardId,
                                 @RequestParam("amount") BigDecimal amount,
                                 RedirectAttributes redirectAttributes, HttpSession session) {
        try {


            int accountId = (int) session.getAttribute("accountId");
            String message = "";
            if (amount.compareTo(BigDecimal.ZERO) <= 0) {
                message = "Сумма должна быть больше 0";
                redirectAttributes.addFlashAttribute("message", message);
                return "redirect:/viewCreditCards";
            }

            if (clientService.addMoneyToCard(cardId, amount)) {
                message = "Добавлено " + amount + " $";
            } else {
                message = "Ошибка операции";
            }
            redirectAttributes.addFlashAttribute("message", message);
            return "redirect:/viewCreditCards";
        } catch (Exception e) {
            if (session != null) {
                session.invalidate();
            }
            LOGGER.error("ERROR", e.getMessage());
            return "redirect:/";
        }
    }


    @RequestMapping("/accountPage")
    public String accountPage(HttpSession session, Model model) {
        try {
            int accountId = (int) session.getAttribute("accountId");
            AccountDTO accountDTO = clientService.getAccountByID(accountId);
            model.addAttribute("account", accountDTO);
            return "account-page";
        } catch (Exception e) {
            if (session != null) {
                session.invalidate();
            }
            LOGGER.error("ERROR", e.getMessage());
            return "redirect:/";
        }
    }


    @RequestMapping("/addMoney")
    public String addMoney(HttpSession session,
                           @RequestParam("amount") BigDecimal amount, RedirectAttributes redirectAttributes) {
        try{
        String message = "";
        int accountId = (int) session.getAttribute("accountId");
        if (clientService.addMoney(accountId, amount)) {
            message = "Добавлено " + amount + " $";

        } else {
            message = "Не добавилось ничего";
        }
        AccountDTO accountDTO = clientService.getAccountByID(accountId);
        redirectAttributes.addFlashAttribute("message", message);
        redirectAttributes.addFlashAttribute("account", accountDTO);
        return "redirect:/accountPage";
        } catch (Exception e) {
            if (session != null) {
                session.invalidate();
            }
            LOGGER.error("ERROR", e.getMessage());
            return "redirect:/";
        }
    }

    @RequestMapping("/changeLocale")
    public String changeLocale(HttpServletRequest request, HttpServletResponse
            response, @RequestParam(name = "lang") String lang, HttpSession session) {
        try{
        Locale locale = new Locale(lang);
        localeResolver.setLocale(request, response, locale);
        return "redirect:" + request.getHeader("Referer");
        } catch (Exception e) {
            if (session != null) {
                session.invalidate();
            }
            LOGGER.error("ERROR", e.getMessage());
            return "redirect:/";
        }
    }

    @RequestMapping("/viewCreditCards")
    public String viewCreditCards(HttpSession session, Model model) {
        try{

        int accountId = (int) session.getAttribute("accountId");

        // Добавление данных в модель
        AccountDTO accountDTO = clientService.getAccountByID(accountId);

        model.addAttribute("account", accountDTO);

        // Переход на страницу с кредитными картами
        return "creditCards";
        } catch (Exception e) {
            if (session != null) {
                session.invalidate();
            }
            LOGGER.error("ERROR", e.getMessage());
            return "redirect:/";
        }
    }


    @RequestMapping("/processPayment")
    public String processPayment(@RequestParam("creditCardId") int creditCardId,
                                 @RequestParam("amount") BigDecimal amount,
                                 @RequestParam("cvv") String cvv, RedirectAttributes redirectAttributes, HttpSession session) {
        try{
        String message = "";
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            message = "Сумма не может быть меньше либо равна 0";
            redirectAttributes.addFlashAttribute("message", message);
            return "redirect:/viewCreditCards";
        }
        try {
            CreditCard creditCard = clientService.getCreditCardById(creditCardId);
            if (creditCard == null || !creditCard.getCvv().equals(cvv)) {
                message = "Неверный cvv";
                redirectAttributes.addFlashAttribute("message", message);
                return "redirect:/viewCreditCards";
            }
            if (clientService.paymentByCard(creditCardId, amount)) {
                message = "Операция прошла успешно";
            } else {
                message = "Ошибка операции";

            }
        } catch (IllegalArgumentException e) {
            message = e.getMessage();
        }
        redirectAttributes.addFlashAttribute("message", message);
        return "redirect:/viewCreditCards";
        } catch (Exception e) {
            if (session != null) {
                session.invalidate();
            }
            LOGGER.error("ERROR", e.getMessage());
            return "redirect:/";
        }
    }

    @GetMapping("/addCreditCard")
    public String addCreditCard(HttpSession session, RedirectAttributes redirectAttributes) {
        try{
        int accountId = (int) session.getAttribute("accountId");
        if (clientService.addNewCreditCard(accountId)) {
            redirectAttributes.addFlashAttribute("message", "Карта добавлена");
        } else {
            redirectAttributes.addFlashAttribute("message", "Карта не добавлена");
        }
        return "redirect:/viewCreditCards";
        } catch (Exception e) {
            if (session != null) {
                session.invalidate();
            }
            LOGGER.error("ERROR", e.getMessage());
            return "redirect:/";
        }
    }


    private void sendAccessCodeToPhone(String phone, String accessCode) {
        // Реализация отправки кода на телефон
    }
}
