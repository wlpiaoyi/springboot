package org.wlpiaoyi.utile;

import java.util.UUID;

public class UUIDUtile {

    public static final int UUID32_LENGHT = 32;
    public static final int UUID64_LENGHT = 64;

    /**
     * 获得指定数目的UUID
     * @param number int 需要获得的UUID数量
     * @return String[] UUID数组
     */
    public static String[] getUUID32S(int number){
        if(number < 1){
            return null;
        }
        StringBuilder uuid = new StringBuilder();
        String[] retArray = new String[number];
        for(int i=0;i<number;i++){
            retArray[i] = UUIDUtile.getUUID32();
        }
        return retArray;
    }

    /**
     * 获得指定数目的UUID
     * @param number int 需要获得的UUID数量
     * @return String[] UUID数组
     */
    public static String[] getUUID64S(int number){
        if(number < 1){
            return null;
        }
        StringBuilder uuid = new StringBuilder();
        String[] retArray = new String[number];
        for(int i=0;i<number;i++){
            retArray[i] = UUIDUtile.getUUID64();
        }
        return retArray;
    }


    /**
     * 获得一个UUID
     * @return String UUID
     */
    public static String getUUID64(){
        String uuid32_1 = UUIDUtile.getUUID32();
        String uuid32_2 = UUIDUtile.getUUID32();
        return uuid32_1 + uuid32_2;
    }

    /**
     * 获得一个UUID
     * @return String UUID
     */
    public static String getUUID32(){
        String uuid = UUID.randomUUID().toString().replace("-", "");
        return uuid;
    }
}
