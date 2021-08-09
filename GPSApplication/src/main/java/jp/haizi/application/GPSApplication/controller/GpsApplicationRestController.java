package jp.haizi.application.GPSApplication.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jp.haizi.application.GPSApplication.dto.CreateLogDto;
import jp.haizi.application.GPSApplication.dto.PostYouIdDto;
import jp.haizi.application.GPSApplication.entity.Log;
import jp.haizi.application.GPSApplication.entity.User;
import jp.haizi.application.GPSApplication.service.LogService;
import jp.haizi.application.GPSApplication.service.YouIdService;

@RestController
@RequestMapping("/api")
public class GpsApplicationRestController {
    @Autowired
    YouIdService yService;
    @Autowired
    LogService logService;

    /**
     * ユーザ登録を行うRESTAPI
     * @param form
     * @return
     */
    @PostMapping("/user/create")
    public User createUser(@RequestBody PostUsernameForm form) {
        PostYouIdDto dto = new PostYouIdDto();
        dto.setNickname(form.getUsername());
        dto.setName(form.getUsername() + ":GPSApplication");
        dto.setEmail("GPSApplicaton@example.com");
        dto.setBirthday("20210808");
        return yService.createUser(dto);
    }

    /**
     * ログ登録を行うRESTAPI
     * @param form
     * @return
     */
    @PostMapping("/log/create")
    public Log createLog(@RequestBody CreateLogForm form) {
        CreateLogDto dto = new CreateLogDto();
        dto.setLatitude(Double.parseDouble(form.getLatitude()));
        dto.setLongitude(Double.parseDouble(form.getLongitude()));
        dto.setName(form.getName());
        dto.setUid(form.getUid());
        dto.setLogDate(new Date());
        return logService.createLog(dto);
    }
}
