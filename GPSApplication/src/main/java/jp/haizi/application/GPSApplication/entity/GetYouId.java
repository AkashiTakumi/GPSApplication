package jp.haizi.application.GPSApplication.entity;

import javax.persistence.Entity;

import lombok.Data;

@Entity
@Data
/**
 * YouIdサービスと通信した際の結果をバインドするクラス
 */
public class GetYouId {
    String uid;
    String name;
    String nickname;
    String email;
    String birthday;
}
