package jp.haizi.application.GPSApplication.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class User {
    @Id
    String uid; //これはyouIdサービスで発行したハッシュ値

    String username;
}
