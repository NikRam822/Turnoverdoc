package com.turnoverdoc.turnover.messenger.telegram;

import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
public class TelegramRequest {
    public String orderStatus;
    public String username;
    public String applicationId;
}
