package jp.haizi.application.GPSApplication.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GpsApplicationController {
    /**
     * メイン画面を表示させるメソッド
     * @param model
     * @return
     */
    @GetMapping("/index")
    public String showIndex(Model model) {
        return "index";
    }
}
