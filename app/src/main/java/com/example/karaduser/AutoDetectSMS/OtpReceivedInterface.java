package com.example.karaduser.AutoDetectSMS;

public interface OtpReceivedInterface {
    void onOtpReceived(String otp);
    void onOtpTimeout();
}
