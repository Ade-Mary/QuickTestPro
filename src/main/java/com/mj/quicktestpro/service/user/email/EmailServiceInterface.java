package com.mj.quicktestpro.service.user.email;

import com.mj.quicktestpro.dto.email.EmailDetails;

public interface EmailServiceInterface {

    String sendSimpleMessage(EmailDetails emailDetails);

    String sendMessageWithAttachment(EmailDetails emailDetails);
}
