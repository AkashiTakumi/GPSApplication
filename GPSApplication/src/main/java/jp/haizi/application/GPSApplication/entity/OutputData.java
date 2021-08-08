package jp.haizi.application.GPSApplication.entity;

//import javax.persistence.Entity;

import lombok.Data;

/**
 * 国土地理院APIからの返り値をバインドするクラス
 */
@Data
public class OutputData {
    Double geoLength;
    Double azimuth1;
    Double azimuth2;
}
