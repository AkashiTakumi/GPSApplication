package jp.haizi.application.GPSApplication.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

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
     * 最新のログを保存するときに同時に前のものと距離を測って返す
     * トランザクション処理
     * @param dto
     * @return
     */
    @Transactional
    public Double service(CreateLogDto dto){
        createLog(dto);
        List<Log> list = getLatestLogs(dto.getUid());
        if (list.size() == 2) {
            StartEndDto startEndDto = new StartEndDto();
            startEndDto.setLatitudeA(list.get(0).getLatitude());
            startEndDto.setLongitudeA(list.get(0).getLongitude());
            startEndDto.setLatitudeB(list.get(1).getLatitude());
            startEndDto.setLongitudeB(list.get(1).getLongitude());
            return getDistance(startEndDto);
        } else {
            // 一件目を保存したときは取られるリストの要素が一つのみ
            // 距離とかがないから0を返すようにした
            return 0.0;
        }
    }

    /**
     * ログの保存
     * @param dto
     * @return
     */
    public Log createLog(CreateLogDto dto){
        Log log = dto.toEntity();
        log.setLogDate(new Date());
        return repo.save(log);
    }

    /**
     * 直近二件の座標情報を取得する
     * もし一件しかなければそれを返す
     * @return
     */
    public List<Log> getLatestLogs(String uid) {
        // まずuidに紐づくログをgetしてlistに格納
        Iterable<Log> found = repo.findByUid(uid);
        List<Log> list = new ArrayList<Log>();
        found.forEach(list::add);

        if (list.size() >= 2) {
            // 直近の二件を取得してくる(StartとEndの座標を抽出するため)
            List<Log> latestList = new ArrayList<Log>();
            latestList.add(list.get(list.size()-2));
            latestList.add(list.get(list.size()-1));

            return latestList;
        } else {
            return list;
        }
    }

    /**
     * 二件見つかった場合にのみ呼び出される
     * @param dto
     * @return
     */
    public Double getDistance(StartEndDto dto) {
        // エンドポイントの作成
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
