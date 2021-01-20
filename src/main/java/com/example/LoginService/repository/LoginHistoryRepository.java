package com.example.LoginService.repository;

import com.example.LoginService.entity.LoginHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

public interface LoginHistoryRepository extends JpaRepository<LoginHistory,String> {

    @Transactional
    @Query(value="select * from loginhistory where email=:userId",nativeQuery=true)
    ArrayList<LoginHistory> getLoginHistoryByUserId(@Param("userId")String userId);


}
