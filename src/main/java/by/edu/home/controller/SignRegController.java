package by.edu.home.controller;

import by.edu.home.entity.Client;
import by.edu.home.service.ClientService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
public class SignRegController {

    private final static Logger LOGGER = LogManager.getRootLogger();

    @Autowired
    private ClientService clientService;


    @RequestMapping("/")
    public String homePage() {
        return "login";
    }


    @RequestMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("client", new Client());
        return "register";
    }

    @RequestMapping("/registerProcess")
    public String processRegistration(@ModelAttribute("client") @Valid Client client, BindingResult bindingResult,
                                      RedirectAttributes redirectAttributes, HttpSession session) {
        if (bindingResult.hasErrors()) {
            return "/register";
        }

        String message = "";
        try {
            clientService.registration(client);
            message = "Registration was successful";
        } catch (IllegalArgumentException e) {
            message = e.getMessage();
            LOGGER.warn(e.getMessage());
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "REGISTRATION ERROR");
            if (session != null) {
                session.invalidate();
            }
            LOGGER.error("ERROR", e.getMessage());
            return "redirect:/";
        }
        redirectAttributes.addFlashAttribute("message", message);
        return "redirect:/";
    }

    @PostMapping("/login")
    public String login(@RequestParam("phone") String phone, @RequestParam("password") String password,
                        Model model, RedirectAttributes redirectAttributes, HttpSession session) {
        // Проверка а не админ ли ты?
        String message = "";
        try {
            int adminId = clientService.checkAdmin(phone, password);
            if (adminId > 0) {
                session.setAttribute("adminId", adminId);
                return "redirect:/admin";
            }
            //если админ, то ретурн на админскую страницу, если нет, то
            // Проверка наличия клиента в базе данных
            int accountId = clientService.checkClientInDatabase(phone, password);

            // Генерация кода доступа
            String accessCode = String.valueOf((int) (Math.random() * 9000) + 1000);

            model.addAttribute("accessCode", accessCode);
            model.addAttribute("phone", phone);
            session.setAttribute("accountId", accountId);

            // Здесь как-будто отправка кода на номер телефона
//            sendAccessCodeToPhone(phone, accessCode);

            return "accessCode";
        } catch (IllegalArgumentException e) {
            message = e.getMessage();
            redirectAttributes.addFlashAttribute("message", message);
            if (session != null) {
                session.invalidate();
            }
            LOGGER.warn("WARN", e.getMessage());
            return "redirect:/";
        } catch (Exception e) {
            if (session != null) {
                session.invalidate();
            }
            LOGGER.error("ERROR", e.getMessage());
            return "redirect:/";
        }
    }


    @PostMapping("/verifyCode")
    public String verifyCode(@RequestParam("code") String code,
                             @RequestParam("accessCode") String accessCode,
                             RedirectAttributes redirectAttributes, HttpSession session) {

        try {

            if (accessCode.equals(code)) {
                return "redirect:/accountPage";
            } else {
                if (session != null) {
                    session.invalidate();
                }
                redirectAttributes.addFlashAttribute("message", "Invalid access code");
                return "redirect:/";
            }
        } catch (Exception e) {
            if (session != null) {
                session.invalidate();
            }
            LOGGER.error("ERROR", e.getMessage());
            return "redirect:/";
        }
    }

    @RequestMapping("/signOut")
    public String signOut(HttpSession session) {
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/";
    }

}
