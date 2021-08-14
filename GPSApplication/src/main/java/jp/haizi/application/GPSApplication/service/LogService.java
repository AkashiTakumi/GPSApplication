package jp.haizi.application.GPSApplication.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import jp.haizi.application.GPSApplication.dto.CreateLogDto;
import jp.haizi.application.GPSApplication.dto.StartEndDto;
import jp.haizi.application.GPSApplication.entity.Log;
import jp.haizi.application.GPSApplication.repository.LogRepository;

@Service
public class LogService {
    @Autowired
    LogRepository repo;

    RestTemplate restTemplate = new RestTemplate();
    public static String endpoint = "http://vldb.gsi.go.jp/sokuchi/surveycalc/surveycalc/bl2st_calc.pl?outputType=json&ellipsoid=GRS80&";

    //世界観測値系
    public static final double GRS80_A = 6378137.000;//長半径 a(m)
    public static final double GRS80_E2 = 0.00669438002301188;//第一遠心率  eの2乗

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
        System.out.println(list);
        if (list.size() == 2) {
            StartEndDto startEndDto = new StartEndDto();
            startEndDto.setLatitudeA(list.get(0).getLatitude());
            startEndDto.setLongitudeA(list.get(0).getLongitude());
            startEndDto.setLatitudeB(list.get(1).getLatitude());
            startEndDto.setLongitudeB(list.get(1).getLongitude());
            System.out.println(startEndDto);
            return getDistance(startEndDto.getLatitudeA(), startEndDto.getLongitudeA(), startEndDto.getLatitudeB(), startEndDto.getLongitudeB());
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

    public static double deg2rad(double deg){
        return deg * Math.PI / 180.0;
    }

    public static double getDistance(double lat1, double lng1, double lat2, double lng2){
        double my = deg2rad((lat1 + lat2) / 2.0); //緯度の平均値
        double dy = deg2rad(lat1 - lat2); //緯度の差
        double dx = deg2rad(lng1 - lng2); //経度の差

        //卯酉線曲率半径を求める(東と西を結ぶ線の半径)
        double sinMy = Math.sin(my);
        double w = Math.sqrt(1.0 - GRS80_E2 * sinMy * sinMy);
        double n = GRS80_A / w;

        //子午線曲線半径を求める(北と南を結ぶ線の半径)
        double mnum = GRS80_A * (1 - GRS80_E2);
        double m = mnum / (w * w * w);

        //ヒュベニの公式
        double dym = dy * m;
        double dxncos = dx * n * Math.cos(my);
        return Math.sqrt(dym * dym + dxncos * dxncos);
    }
}
