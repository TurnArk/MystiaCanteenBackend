package com.javaweb.mystiacanteen.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.javaweb.mystiacanteen.entity.TalkMessage;
import com.javaweb.mystiacanteen.mapper.TalkMessageMapper;
import com.javaweb.mystiacanteen.service.TalkMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TalkMessageServiceImpl extends ServiceImpl<TalkMessageMapper, TalkMessage> implements TalkMessageService {
    @Autowired
    private TalkMessageMapper talkMessageMapper;

    @Override
    public List<TalkMessage> getMessage(String talk_with, String type, int productionId) {
        System.out.println(talkMessageMapper.getMessage(talk_with, type, productionId)+talk_with+type+productionId);
        return talkMessageMapper.getMessage(talk_with, type, productionId);
    }

    @Override
    public void delMessage(int id) {
        talkMessageMapper.deleteMessage(id);
    }

    @Override
    public Boolean addMessage(String username, String message, String talk_with, int productionId, String type, LocalDateTime time){
        return talkMessageMapper.addMessage(username, message, talk_with, productionId, type, time);
    }

}
