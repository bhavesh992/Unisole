package com.example.LoginService.services.impl;

import com.example.LoginService.entity.LoginHistory;
import com.example.LoginService.repository.LoginHistoryRepository;
import com.example.LoginService.services.LoginHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class LoginHistoryServiceImpl implements LoginHistoryService {
    @Autowired
    private LoginHistoryRepository loginHistoryRepository;
    @Override
    public void logHistory(LoginHistory loginHistory) {
        loginHistoryRepository.save(loginHistory);
    }

    @Override
    public ArrayList<LoginHistory> getLast180DaysHistory(String email) {
        return loginHistoryRepository.getLoginHistoryByUserId(email);
    }


}
