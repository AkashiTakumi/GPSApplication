package jp.haizi.application.GPSApplication.entity;

import javax.persistence.Entity;

import lombok.Data;

/**
 * APIからの返り値をバインドするクラス
 */
@Entity
@Data
public class OutputData {
    Double geoLength;
    Double azimuth1;
    Double azimuth2;
}
