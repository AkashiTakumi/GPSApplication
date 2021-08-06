package jp.haizi.application.GPSApplication.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import jp.haizi.application.GPSApplication.dto.CreateLogDto;
import jp.haizi.application.GPSApplication.dto.StartEndDto;
import jp.haizi.application.GPSApplication.entity.Log;
import jp.haizi.application.GPSApplication.entity.OutputData;
import jp.haizi.application.GPSApplication.repository.LogRepository;

@Service
public class LogService {
    @Autowired
    LogRepository repo;

    RestTemplate restTemplate = new RestTemplate();
    public static String endpoint = "http://vldb.gsi.go.jp/sokuchi/surveycalc/surveycalc/bl2st_calc.pl?outputType=json&ellipsoid=GRS80&";

    /**
     * ログの保存
     * @param dto
     * @return
     */
    public Log createLog(CreateLogDto dto){
        return repo.save(dto.toEntity());
    }

    /**
     * 直近二件の座標情報を取得する
     * @return
     */
    public List<Log> getLatestLogs() {
        // まずすべての情報をgetしてlistに格納
        Iterable<Log> found = repo.findAll();
        List<Log> list = new ArrayList<Log>();
        found.forEach(list::add);

        // 直近の二件を取得してくる(StartとEndの座標を抽出するため)
        List<Log> latestList = new ArrayList<Log>();
        latestList.add(list.get(list.size()-2));
        latestList.add(list.get(list.size()-1));

        return latestList;
    }

    public Double getDistance(StartEndDto dto) {
        endpoint = endpoint + "latitude1=" + dto.getLatitudeA() + "&longitude1=" + dto.getLongitudeA() + "&latitude2=" + dto.getLatitudeB() + "&longitude2=" + dto.getLongitudeB();
        System.out.println(endpoint);
        ResponseEntity<OutputData> responseEntity = restTemplate.getForEntity(endpoint, OutputData.class);
        HttpStatus statusCode = responseEntity.getStatusCode();
        System.out.println(statusCode);
        OutputData outputData = responseEntity.getBody();

        Double distance = outputData.getGeoLength();

        return distance;
    }
}
