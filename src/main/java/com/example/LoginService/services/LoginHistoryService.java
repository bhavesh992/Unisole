package com.example.LoginService.services;

import com.example.LoginService.entity.LoginHistory;

import java.util.ArrayList;

public interface LoginHistoryService {
    void logHistory(LoginHistory loginHistory);
    ArrayList<LoginHistory> getLast180DaysHistory(String email);
}
