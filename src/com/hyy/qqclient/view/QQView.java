package com.hyy.qqclient.view;

import com.hyy.qqclient.service.FileClientService;
import com.hyy.qqclient.service.MessageClientService;
import com.hyy.qqclient.service.UserClientService;
import com.hyy.qqclient.utils.Utility;

/**
 * Description
 *
 * @author helaxest
 * @date 2021/04/19  13:55
 * @since
 */
public class QQView {
    private boolean loop = true;
    private String key = "";
    private UserClientService userClientService=new UserClientService();
    private MessageClientService messageClientService=new MessageClientService();
    private FileClientService fileClientService=new FileClientService();

    public static void main(String[] args) {
        new QQView().mainView();
    }

    //显示主菜单
    public void mainView() {
        while (loop) {
            System.out.println("===========欢迎登录网络通信系统============");
            System.out.println("\t\t 1 登录系统");
            System.out.println("\t\t 9 退出系统");
            System.out.print("请输入你的选择: ");
            key = Utility.readString(1);
            switch (key) {
                case "1":
                    System.out.print("请输入用户号: ");
                    String userId = Utility.readString(50);
                    System.out.print("请输入密  码: ");
                    String pwd = Utility.readString(50);
                    if (userClientService.checkUser(userId, pwd)) {
                        System.out.println("===========欢迎用户" + userId + "登录成功============");
                        while (loop) {
                            System.out.println("=======二级菜单(用户" + userId + ")=======");
                            System.out.println("\t\t 1 显示在线用户列表");
                            System.out.println("\t\t 2 群发消息");
                            System.out.println("\t\t 3 私聊消息");
                            System.out.println("\t\t 4 发送文件");
                            System.out.println("\t\t 9 退出系统");
                            System.out.println("请输入你的选择: ");
                            key = Utility.readString(1);
                            switch (key) {
                                case "1":
                                   userClientService.onLineFriendList();
                                    break;
                                case "2":
                                    System.out.print("请输入内  容  ");
                                    String content = Utility.readString(50);
                                    messageClientService.sendMessageToAll(content,userId);
                                    break;
                                case "3":
                                    System.out.print("请输入想要聊天用户的Id: ");
                                    String getterId = Utility.readString(50);
                                    System.out.print("请输入内  容  ");
                                    String content2 = Utility.readString(50);
                                    messageClientService.sendMessageToOne(getterId,content2,userId);
//                                    System.out.println("私聊消息");

                                    break;
                                case "4":
                                    System.out.print("请输入收取文件的用户的Id: ");
                                    getterId=Utility.readString(50);
                                    System.out.print("请输入发送文件的路径: ");
                                    String src=Utility.readString(50);
                                    System.out.print("请输入对方保存文件的路径: ");
                                    String dest=Utility.readString(50);
                                    fileClientService.sendFileToOne(src, dest, userId, getterId);
                                    break;
                                case "9":
                                    loop = false;
                                    //给server发送退出的消息
                                    userClientService.logout();

                                    //停止线程
                                    break;
                            }
                        }
                    } else {
                        System.out.println("登录失败");
                    }
                    break;
                case "9":
                    loop = false;
                    break;
            }

        }

        System.out.println("客户端退出....");

    }
}
