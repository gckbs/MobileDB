package com.example.mobiledb;

public interface Callback{
    void success(String str, Food food);
    void fail(String errorMessage);
}
