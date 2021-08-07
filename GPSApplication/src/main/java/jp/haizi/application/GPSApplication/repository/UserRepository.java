package jp.haizi.application.GPSApplication.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import jp.haizi.application.GPSApplication.entity.User;

@Repository
public interface UserRepository extends CrudRepository<User, String>{
    /**
     * ログイン時にユーザネームで登録されているユーザを検索する
     * @param username
     * @return
     */
    public User findByUsername(String username);
}
