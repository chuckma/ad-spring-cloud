package cn.lucas.ad.dao;

import cn.lucas.ad.entity.AdUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdUserRepository extends JpaRepository<AdUser, Long> {


    /**
     * 根据 username 查询一个对象 AdUser
     * @param username
     * @return
     */
    AdUser findByUsername(String username);
}
