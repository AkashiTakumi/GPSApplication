package jp.haizi.application.GPSApplication.entity;

//import javax.persistence.Entity;

import lombok.Data;

/**
 * 国土地理院APIからの返り値をバインドするクラス
 */
@Data
public class OutputData {
    String geoLength;
    String azimuth1;
    String azimuth2;
}
