package jp.haizi.application.GPSApplication.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import jp.haizi.application.GPSApplication.dto.CreateUserDto;
import jp.haizi.application.GPSApplication.dto.PostYouIdDto;
import jp.haizi.application.GPSApplication.entity.GetYouId;
import jp.haizi.application.GPSApplication.entity.User;
import jp.haizi.application.GPSApplication.exception.UserException;
import jp.haizi.application.GPSApplication.repository.UserRepository;

@Service
public class YouIdService {
    @Autowired
    UserRepository urepo;

    RestTemplate restTemplate = new RestTemplate();
    public static final String url = "https://wsapp.cs.kobe-u.ac.jp/YouId/api";

    /**
     * youIdを取得する
     * @param dto
     * @return
     */
    public String getYouId(PostYouIdDto dto) {
        GetYouId userEntity = restTemplate.postForObject(url, dto, GetYouId.class);
        String uid = userEntity.getUid();
        return uid;
    }

    /**
     * ユーザ登録をするメソッド
     * @param dto
     * @return
     */
    public User createUser(PostYouIdDto dto) {
        if (urepo.count() == 0) {
            CreateUserDto createUserDto = new CreateUserDto();
            createUserDto.setUid(getYouId(dto));
            createUserDto.setUsername(dto.getNickname());
            return urepo.save(createUserDto.toEntity());
        } else {
            if (urepo.findByUsername(dto.getNickname()) == null) {
                throw new UserException(UserException.EXISTED_USER, "That username existed!!");
            } else {
                CreateUserDto createUserDto = new CreateUserDto();
                createUserDto.setUid(getYouId(dto));
                createUserDto.setUsername(dto.getNickname());
                return urepo.save(createUserDto.toEntity());
            }
        }
    }
}
