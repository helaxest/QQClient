package com.hyy.qqclient.service;

import com.hyy.qqcommon.Message;
import com.hyy.qqcommon.MessageType;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Date;

/**
 * Description
 *
 * @author helaxest
 * @date 2021/04/20  13:29
 * @since
 */
@SuppressWarnings({"all"})
public class MessageClientService {
    /**
     *
     * @param getterId 收消息人Id
     * @param content   内容
     * @param senderId 发消息人Id
     */
    public void sendMessageToOne(String getterId, String content, String senderId){
        Message message = new Message();
        message.setMesType(MessageType.MESSAGE_COMM_MES);
        message.setSender(senderId);
        message.setGetter(getterId);
        message.setContent(content);
        message.setSendTime(new Date().toString());
        System.out.println(senderId+"对"+getterId+"说"+content);
        //发送给服务器
        try {
            ObjectOutputStream oos = new ObjectOutputStream(ManageClientConnectServerThread
                    .getClientConnectServerThread(senderId)
                    .getSocket().getOutputStream());
            oos.writeObject(message);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 群发
     * @param content 内容
     * @param senderId 发送者Id
     */
    public void sendMessageToAll(String content, String senderId){
        Message message = new Message();
        message.setMesType(MessageType.MESSAGE_TO_ALL_MES);
        message.setSender(senderId);
        message.setContent(content);
        message.setSendTime(new Date().toString());
        System.out.println(senderId+"对所有人说 :"+content);
        //发送给服务器
        try {
            ObjectOutputStream oos = new ObjectOutputStream(ManageClientConnectServerThread
                    .getClientConnectServerThread(senderId)
                    .getSocket().getOutputStream());
            oos.writeObject(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
