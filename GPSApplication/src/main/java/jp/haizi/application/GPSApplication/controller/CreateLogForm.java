package jp.haizi.application.GPSApplication.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateLogForm {
    String latitude;
    String longitude;
    String name;
    String uid;
}
