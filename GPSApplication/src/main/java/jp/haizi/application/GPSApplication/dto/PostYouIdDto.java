package jp.haizi.application.GPSApplication.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostYouIdDto {
    String name;

    String nickname;

    String email;

    String birthday;
}
