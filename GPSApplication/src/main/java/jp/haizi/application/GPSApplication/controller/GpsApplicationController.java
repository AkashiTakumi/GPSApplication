package jp.haizi.application.GPSApplication.controller;

import java.util.Date;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import jp.haizi.application.GPSApplication.dto.CreateLogDto;
import jp.haizi.application.GPSApplication.dto.PostYouIdDto;
import jp.haizi.application.GPSApplication.entity.User;
import jp.haizi.application.GPSApplication.repository.UserRepository;
import jp.haizi.application.GPSApplication.service.LogService;
import jp.haizi.application.GPSApplication.service.YouIdService;

@Controller
public class GpsApplicationController {
    @Autowired
    UserRepository urepo;
    @Autowired
    YouIdService youIdService;
    @Autowired
    LogService logService;

    /**
     * サインアップ画面を表示させるメソッド
     * @param uid
     * @param model
     * @return
     */
    @GetMapping("/signup")
    public String showSignupPage(@ModelAttribute(name = "postUsernameForm") PostUsernameForm form, Model model) {
        model.addAttribute("postUsernameForm", form);
        return "signup";
    }

    /**
     * サインアップ処理をするメソッド
     * @param form
     * @param model
     * @return
     */
    @PostMapping("/signup")
    public String signup(@ModelAttribute(name = "postUsernameForm") PostUsernameForm form, Model model) {
        PostYouIdDto dto = new PostYouIdDto();
        dto.setNickname(form.getUsername());
        dto.setName(form.getUsername() + ":GPSApplication");
        dto.setEmail("GPS" + form.getUsername() +"@example.com");
        dto.setBirthday("20210809");
        System.out.println(dto);
        youIdService.createUser(dto);
        return "redirect:/login";
    }

    /**
     * ログインページを表示するメソッド
     * @param uid
     * @param model
     * @return
     */
    @GetMapping("/login")
    public String showLoginPage(@ModelAttribute(name = "loginForm") LoginForm form, Model model) {
        model.addAttribute("loginForm", form);
        return "login";
    }

    /**
     * ログイン時に呼び出すメソッド
     * @param response
     * @param form
     * @param model
     * @return
     */
    @PostMapping("/login")
    public String login(HttpServletResponse response, @ModelAttribute(name = "loginForm") LoginForm form, Model model) {
        User user = urepo.findByUsername(form.getUsername());
        if (user != null) {
            Cookie cookie = new Cookie("uid", user.getUid());
            cookie.setMaxAge(256*24*60*60);
            cookie.setPath("/");
            response.addCookie(cookie);
            return "redirect:/";
        } else {
            return "redirect:/login";
        }
    }

    /**
     * ログインしてsetCookieしたuidを取得，それでユーザ検索かけて，存在したらindexを表示
     * 存在しなければ，有効期限が切れているか登録していないので，ログイン画面にリダイレクトする
     * @param uid
     * @param model
     * @return
     */
    @GetMapping("/")
    public String showIndexPage(@CookieValue(name="uid", required = false, defaultValue = "abc")String uid, 
    @ModelAttribute(name = "createLog") CreateLogDto dto, Double distance, Model model) {
        if (urepo.existsById(uid)) {
            User user = urepo.findByUid(uid);
            model.addAttribute("username", user.getUsername());
            model.addAttribute("createLog", dto);
            if (distance == null) {
                distance = 0.0;
            }
            model.addAttribute("distance", distance);
            return "index";
        } else {
            return "redirect:/login";
        }
    }

    /**
     * GPSを取得する際に呼び出されるメソッド
     * @param uid
     * @param dto
     * @param model
     * @return
     */
    @PostMapping("/")
    public String getLength(@CookieValue(name="uid", required = false, defaultValue = "abc")String uid, 
    @CookieValue(name="latitude", required = false, defaultValue = "35.0")String latitude, 
    @CookieValue(name="longitude", required = false, defaultValue = "135.0")String longitude, 
    @ModelAttribute(name = "createLog") CreateLogDto dto, Model model) {
        if (urepo.existsById(uid)) {
            User user = urepo.findByUid(uid);
            model.addAttribute("username", user.getUsername());
            model.addAttribute("createLog", dto);
            dto.setUid(uid);
            dto.setLatitude(Double.parseDouble(latitude));
            dto.setLongitude(Double.parseDouble(longitude));
            dto.setLogDate(new Date());
            Double distance = logService.service(dto);
            System.out.println(distance);
            model.addAttribute("distance", distance);
            return "index";
        } else {
            return "redirect:/login";
        }
    }

    /**
     * ログアウトに投げられるメソッド
     * @param request
     * @param response
     * @return
     * @throws RuntimeException
     */
    @PostMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) throws RuntimeException {
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if("uid".equals(cookie.getName())) {
                cookie.setMaxAge(0);
                cookie.setPath("/");
                response.addCookie(cookie);
            }
        }
        return "redirect:/login";
    }
}
