package com.hyy.qqclient.service;

import com.hyy.qqcommon.Message;
import com.hyy.qqcommon.MessageType;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.Vector;

/**
 * Description
 *
 * @author helaxest
 * @date 2021/04/20  7:43
 * @since
 */
public class ClientConnectServerThread extends Thread {

    private Socket socket;

    public ClientConnectServerThread(Socket socket) {
        this.socket = socket;
    }
    @Override
    public void run() {
        while (true) {
            System.out.println("客户端线程,等待读取服务器发送的消息");
            try {
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                //没有信息的话,会阻塞
                Message message = (Message) ois.readObject();
                if(message.getMesType().equals(MessageType.MESSAGE_RET_ONLINE_FRIEND)){
                    String[] onlineUsers = message.getContent().split(" ");
                    System.out.println("\n==========当前用户列表如下=========");
                    for (int i = 0; i < onlineUsers.length; i++) {
                        System.out.println("用户: "+onlineUsers[i]);
                    }
                }else if(message.getMesType().equals(MessageType.MESSAGE_COMM_MES)){
                    System.out.print("["+message.getSender()+"] 对 ["+message.getGetter()+"]说:  "+message.getContent()+"\t\t");
                    System.out.println(message.getSendTime());
                }
                else if(message.getMesType().equals(MessageType.MESSAGE_TO_ALL_MES)){
                    System.out.print("["+message.getSender()+"] 对 [大家]说:  "+message.getContent()+"\t\t");
                    System.out.println(message.getSendTime());
                }else if(message.getMesType().equals(MessageType.MESSAGE_FILE_MES)){
                    System.out.println("\n"+message.getSender()+"给"+message.getGetter()+"发文件："
                    +message.getSrc()+"到我的电脑目录"+message.getDest());
                    FileOutputStream fis = new FileOutputStream(message.getDest());
                    fis.write(message.getFileByte());
                    fis.close();
                    System.out.println("保存文件成功");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public Socket getSocket() {
        return socket;
    }
}
