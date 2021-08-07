package jp.haizi.application.GPSApplication.dto;

import jp.haizi.application.GPSApplication.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserDto {
    String uid;
    String username;

    public User toEntity() {
        User user = new User();
        user.setUid(uid);
        user.setUsername(username);

        return user;
    }
}
