package com.hyy.qqclient.service;


import java.util.HashMap;

/**
 * Description
 *
 * @author helaxest
 * @date 2021/04/20  8:03
 * @since
 */
public class ManageClientConnectServerThread {

    /**
     * key:userId
     * value:线程
     */
    private static HashMap<String, ClientConnectServerThread> hm = new HashMap<>();

    /**
     * 将线程加入到集合
     * @param userId 用户id
     * @param clientConnectServerThread 线程
     */
    public static void addClientConnectServerThread(String userId, ClientConnectServerThread clientConnectServerThread) {
        hm.put(userId, clientConnectServerThread);
    }

    /**
     * 从集合里的到线程
     * @param userId 用户id
     * @return
     */
    public static ClientConnectServerThread getClientConnectServerThread(String userId){

        return  hm.get(userId);
    }
}
