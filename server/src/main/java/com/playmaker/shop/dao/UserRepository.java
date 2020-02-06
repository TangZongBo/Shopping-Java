package com.playmaker.shop.dao;

import com.playmaker.shop.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @ProjectName: Shopping-Java
 * @Package: com.playmaker.shop.dao
 * @ClassName: UserRepository
 * @Author: shangqianzhushang
 * @Description:
 * @Date: 2020/2/6 20:17
 * @Version: 1.0
 */
public interface UserRepository extends JpaRepository<User,Integer> {
         User findByUsername(String username);
}
