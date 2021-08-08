package jp.haizi.application.GPSApplication.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jp.haizi.application.GPSApplication.dto.PostYouIdDto;
import jp.haizi.application.GPSApplication.entity.User;
import jp.haizi.application.GPSApplication.service.YouIdService;

@RestController
@RequestMapping("/api")
public class GpsApplicationRestController {
    @Autowired
    YouIdService yService;
    @PostMapping("/user/create")
    public User createUser(@RequestBody PostUsernameForm form) {
        PostYouIdDto dto = new PostYouIdDto();
        dto.setNickname(form.getUsername());
        dto.setName(form.getUsername() + ":GPSApplication");
        dto.setEmail("GPSApplicaton@example.com");
        dto.setBirthday("20210808");
        return yService.createUser(dto);
    }
}
