package jp.haizi.application.GPSApplication.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class Log {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long logId;

    // 緯度
    Double latitude;

    // 経度
    Double longitude;

    // 場所の名前
    String name;

    // 紐づけるユーザのid
    String uid;

    // ログを取った時間
    Date logDate;
}
