package com.avocado.notification.push;

public interface NotificationService {
    void sendNotificationByToken(NotificationMessage notificationMessage);
}
