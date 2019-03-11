package be.vdab.frida.controllers;

import be.vdab.frida.domain.Adres;
import be.vdab.frida.domain.Gemeente;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import java.time.DayOfWeek;
import java.time.LocalDate;

@Controller
@RequestMapping("/")
class IndexController {
    @GetMapping
    ModelAndView index(@CookieValue(name = "reedsBezocht", required = false)
                               String reedsBezocht, HttpServletResponse response){
        Cookie cookie = new Cookie("reedsBezocht", "ja");
        cookie.setMaxAge(31_536_000);
        response.addCookie(cookie);

        DayOfWeek weekdag = LocalDate.now().getDayOfWeek();
        String boodschap =
                weekdag == DayOfWeek.MONDAY || weekdag == DayOfWeek.THURSDAY ?
                        "gesloten" : "open";
        ModelAndView modelAndView = new ModelAndView("index", "boodschap", boodschap);
        if(reedsBezocht != null){
            modelAndView.addObject("reedsBezocht", true);
        }
        modelAndView.addObject(new Adres("Grote markt", "7", new Gemeente("Brussel", 1000)));
        return  modelAndView;
    }
}
