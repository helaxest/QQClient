package com.hyy.qqclient.service;

import com.hyy.qqcommon.Message;
import com.hyy.qqcommon.MessageType;
import com.hyy.qqcommon.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Date;

/**
 * Description
 *
 * @author helaxest
 * @date 2021/04/20  7:17
 * @since
 */
@SuppressWarnings({"all"})
public class UserClientService {
    private User u = new User();
    private Socket socket;

    /**
     * 到服务器验证 用户名 密码
     *
     * @param userId 用户名
     * @param pwd    密码
     * @return
     */
    public boolean checkUser(String userId, String pwd) {
        u.setUserId(userId);
        u.setPassword(pwd);
        boolean b = false;
        try {
            //链接服务器
            socket = new Socket(InetAddress.getLocalHost(), 9999);
            //发送user
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(u);

            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            Message ms = (Message) ois.readObject();//读取message
            //判断message
            if (ms.getMesType().equals(MessageType.MESSAGE_LOGIN_SUCCEED)) {

                //成功
                ClientConnectServerThread ClientConnectServerThread
                        = new ClientConnectServerThread(socket);
//                ClientConnectServerThread.setDaemon(true);
                ClientConnectServerThread.start();
                //管理线程
                ManageClientConnectServerThread.
                        addClientConnectServerThread(userId, ClientConnectServerThread);
                b = true;

            } else {
                //失败,关闭socket
                socket.close();
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        return b;
    }

    /**
     * 向服务器发送请求在线用户
     */
    public void onLineFriendList() {
        Message message = new Message();
        message.setMesType(MessageType.MESSAGE_GET_ONLINE_FRIEND);
        message.setSender(u.getUserId());//经过校验u此时为当前user

        //发送给服务器
        try {
            ObjectOutputStream oos = new ObjectOutputStream(ManageClientConnectServerThread
                    .getClientConnectServerThread(u.getUserId())
                    .getSocket().getOutputStream());
            oos.writeObject(message);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 退出系统
     */
    public void logout(){
        Message message = new Message();
        message.setMesType(MessageType.MESSAGE_CLIENT_EXIT);
        message.setSender(u.getUserId());
        //发送给服务器
        try {
            ObjectOutputStream oos = new ObjectOutputStream(ManageClientConnectServerThread
                    .getClientConnectServerThread(u.getUserId())
                    .getSocket().getOutputStream());
            oos.writeObject(message);
            System.out.println("用户: " +u.getUserId()+"退出系统");
            System.exit(0);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }



}
