package jp.haizi.application.GPSApplication.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import jp.haizi.application.GPSApplication.entity.Log;

@Repository
public interface LogRepository extends CrudRepository<Log, Long>{
    // 特定のuidに紐づくログを持ってくるメソッド
    public List<Log> findByUid(String uid);
}
