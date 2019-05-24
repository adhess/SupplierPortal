package com.adhess.org.notification.service.notification.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Notification implements Serializable {
    private String username;
    private String role;
    private String value;
}
