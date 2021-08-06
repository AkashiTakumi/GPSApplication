package jp.haizi.application.GPSApplication.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import jp.haizi.application.GPSApplication.entity.Log;

@Repository
public interface LogRepository extends CrudRepository<Log, Long>{
    
}
