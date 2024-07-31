package by.edu.home.controller;

import by.edu.home.entity.Account;
import by.edu.home.service.ClientService;
import by.edu.home.transfer.AdminDTO;
import by.edu.home.transfer.PaymentDTO;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Controller
public class AdminController {
    private final static Logger LOGGER = LogManager.getRootLogger();

    @Autowired
    private ClientService clientService;


    //Добавлено для получения тестового лога
    @RequestMapping("warn")
    public String getWarn(RedirectAttributes redirectAttributes) {
        try {
            throw new IllegalArgumentException("Verification warning");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("message","Verification warning received");
            LOGGER.warn(e.getMessage());
        }
        return "redirect:/";
    }

    //Добавлено для получения тестового лога
    @RequestMapping("error")
    public String getError(RedirectAttributes redirectAttributes) {
        try {
            throw new Exception("Verification error");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error","Verification error received");
            LOGGER.error(e.getMessage());
        }
        return "redirect:/";
    }

    @RequestMapping("/admin")
    public String adminPage(HttpSession session) {
        try {

            int adminId = (int) session.getAttribute("adminId");
            AdminDTO admin = clientService.getAdminByID(adminId);
            session.setAttribute("admin", admin);

            return "admin-page";
        } catch (Exception e) {
            if (session != null) {
                session.invalidate();
            }
            LOGGER.error("ERROR", e.getMessage());
            return "redirect:/";
        }
    }

    @RequestMapping("/admin-info")
    public String adminInfoPage(@RequestParam(value = "phone", required = false) String phone, HttpSession session, Model model) {
        try {
            Integer adminId = (Integer) session.getAttribute("adminId");

            if (adminId == null) {
                return "redirect:/login";
            }

            if (phone == null || phone.isEmpty()) {
                phone = (String) model.asMap().get("phone");
            }
            if (phone != null && !phone.isEmpty()) {
                Account userAccount = clientService.findAccountByClientPhone(phone);
                model.addAttribute("userAccount", userAccount);
            }

            return "admin-page";
        } catch (Exception e) {
            if (session != null) {
                session.invalidate();
            }
            LOGGER.error("ERROR", e.getMessage());
            return "redirect:/";
        }
    }


    @PostMapping("/update-account-status")
    public String updateAccountStatus(@RequestParam("accountStatus") String status, @RequestParam("accountId") int accountId,
                                      @RequestParam("phone") String phone, RedirectAttributes redirectAttributes, HttpSession session) {
        try {
            String message = "";
            if (clientService.changeAccountStatus(accountId, status)) {
                message = "account status has been changed";
            } else {
                message = "failed to change account status";
            }
            redirectAttributes.addFlashAttribute("message", message);
            redirectAttributes.addFlashAttribute("phone", phone);
            return "redirect:/admin-info";

        } catch (Exception e) {
            if (session != null) {
                session.invalidate();
            }
            LOGGER.error("ERROR", e.getMessage());
            return "redirect:/";
        }
    }

    @PostMapping("/update-card-status")
    public String updateCardStatus(@RequestParam("cardStatus") String status,
                                   @RequestParam("cardId") int cardId,
                                   @RequestParam("cardNumber") String cardNumber,
                                   @RequestParam("phone") String phone, RedirectAttributes redirectAttributes, HttpSession session) {
        try {
            String message = "";
            if (clientService.changeCardStatus(cardId, status)) {
                message = "card status " + cardNumber + " has been changed";
            } else {
                message = "failed to change card status " + cardNumber;
            }
            redirectAttributes.addFlashAttribute("message", message);
            redirectAttributes.addFlashAttribute("phone", phone);
            return "redirect:/admin-info";
        } catch (Exception e) {
            if (session != null) {
                session.invalidate();
            }
            LOGGER.error("ERROR", e.getMessage());
            return "redirect:/";
        }

    }

    @RequestMapping("/get-logs")
    public String getLogs(Model model, HttpSession session) {
        try {
            List<String> fileContent = new ArrayList<>();
            String filePath = "a:/WebLogs/payments.log";
            try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
                String line;
                while ((line = br.readLine()) != null) {
                    fileContent.add(line);
                }
            } catch (IOException e) {
                LOGGER.error(e.getMessage());
                if (session != null) {
                    session.invalidate();
                }
                LOGGER.error("ERROR", e.getMessage());
                return "redirect:/";
            }
            model.addAttribute("fileContent", fileContent);
            return "log-info";
        } catch (Exception e) {
            if (session != null) {
                session.invalidate();
            }
            LOGGER.error("ERROR", e.getMessage());
            return "redirect:/";
        }
    }

    @RequestMapping("/get-payments")
    public String getPayments(@RequestParam("phone") String phone, Model model, HttpSession session) {
        try {
            if (phone != null && !phone.isEmpty()) {
                Account userAccount = clientService.findAccountByClientPhone(phone);
                List<PaymentDTO> payments = clientService.getPayments(userAccount);
                model.addAttribute("payments", payments);
            }
            return ("payments-info");
        } catch (Exception e) {
            if (session != null) {
                session.invalidate();
            }
            LOGGER.error("ERROR", e.getMessage());
            return "redirect:/";
        }
    }
}
