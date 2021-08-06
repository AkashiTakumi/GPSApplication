package jp.haizi.application.GPSApplication.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StartEndDto {
    // 出発点の緯度と経度
    Double latitudeA;
    Double longitudeA;

    // 到着店の緯度と経度
    Double latitudeB;
    Double longitudeB;
}
