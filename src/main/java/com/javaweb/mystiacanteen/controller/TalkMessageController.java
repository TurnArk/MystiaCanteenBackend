package com.javaweb.mystiacanteen.controller;

import com.javaweb.mystiacanteen.entity.TalkMessage;
import com.javaweb.mystiacanteen.filter.JwtFilter;
import com.javaweb.mystiacanteen.service.TalkMessageService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
public class TalkMessageController {
    @Autowired
    private TalkMessageService talkMessageService;

    @PostMapping("/selectMessage")
    public List<TalkMessage> selectMessage(@RequestBody TalkMessage talkMessage) {
        System.out.println("Received TalkMessage: " + talkMessage);
        System.out.println("talk_with=" + talkMessage.getTalk_with() + ", type=" + talkMessage.getType() + ", productionId=" + talkMessage.getProductionId());

        String talk_with=talkMessage.getTalk_with();
        String type=talkMessage.getType();
        int productionId=talkMessage.getProductionId();
        return talkMessageService.getMessage(talk_with, type, productionId);
    }

    @PostMapping("/addTalk")
    public String addTalk(@RequestBody TalkMessage talkMessage, HttpServletRequest request) {
        String username=talkMessage.getUsername();
        String talk_with=talkMessage.getTalk_with();
        String type=talkMessage.getType();
        int productionId=talkMessage.getProductionId();
        String message=talkMessage.getMessage();
        LocalDateTime time=LocalDateTime.now();
        if(JwtFilter.validateToken(request,username)){
            if(talkMessageService.addMessage(username, message, talk_with, productionId, type, time))
                return "评论成功";
            else
                return "评论失败";
        }else{
            return "/login";
        }
    }

    @PostMapping("/deleteTalk")
    public void deleteTalk(@RequestParam int id) {
        talkMessageService.delMessage(id);
    }
}
