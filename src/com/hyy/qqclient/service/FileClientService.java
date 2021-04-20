package com.hyy.qqclient.service;

import com.hyy.qqcommon.Message;
import com.hyy.qqcommon.MessageType;

import java.io.*;

/**
 * Description
 *
 * @author helaxest
 * @date 2021/04/20  15:14
 * @since
 */
public class FileClientService {
    /**
     *
     * @param src 源文件路径
     * @param dest 目标文件路径
     * @param senderId 发送者id
     * @param getterId 接收者id
     */
    public  void sendFileToOne(String src, String dest, String senderId, String getterId){
        Message message = new Message();
        message.setMesType(MessageType.MESSAGE_FILE_MES);
        message.setSender(senderId);
        message.setSrc(src);
        message.setDest(dest);
        message.setGetter(getterId);

        FileInputStream fis=null;
        byte[] fileBytes=new byte[((int) new File(src).length())];
        try {
            fis=new FileInputStream(src);
            fis.read(fileBytes);
            message.setFileByte(fileBytes);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(fis!=null){
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println("\n"+senderId+"  给  "+getterId+"发送文件: "+src+"到对方的电脑: "+dest);
        try {
            ObjectOutputStream oos =
                    new ObjectOutputStream(ManageClientConnectServerThread.getClientConnectServerThread(senderId).getSocket().getOutputStream());
            oos.writeObject(message);
        } catch (IOException e){

        }

    }
}
