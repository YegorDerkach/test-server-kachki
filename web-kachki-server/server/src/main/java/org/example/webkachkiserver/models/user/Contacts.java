package org.example.webkachkiserver.models.user;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Contacts {
    private String instUrl;
    private String facebookUrl;
    private String linkedInUrl;
    private String telegramUrl;
}
