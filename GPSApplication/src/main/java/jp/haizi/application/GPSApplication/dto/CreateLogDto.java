package jp.haizi.application.GPSApplication.dto;

import jp.haizi.application.GPSApplication.entity.Log;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateLogDto {
    Double latitude;

    Double longitude;

    String name;

    String uid;

    public Log toEntity() {
        Log log = new Log();

        log.setLatitude(latitude);
        log.setLongitude(longitude);
        log.setName(name);
        log.setUid(uid);

        return log;
    }
}
