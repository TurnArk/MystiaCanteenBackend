package com.javaweb.mystiacanteen.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.javaweb.mystiacanteen.entity.TalkMessage;

import java.time.LocalDateTime;
import java.util.List;

public interface TalkMessageService extends IService<TalkMessage> {
    public List<TalkMessage> getMessage(String talk_with, String type, int productionId);
    public void delMessage(int id);
    public Boolean addMessage(String username, String message, String talk_with, int productionId, String type, LocalDateTime time);
}
