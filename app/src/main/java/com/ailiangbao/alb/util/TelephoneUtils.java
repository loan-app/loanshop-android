package com.ailiangbao.alb.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TelephoneUtils {
    public static List<String> getRandomTelephone() {
//        String res = "^(13[0-9]|14[579]|15[0-3,5-9]|16[6]|17[0135678]|18[0-9]|19[89])\\d{8}$";
        ArrayList<String> list = new ArrayList<>();
        int[] arr = new int[]{3, 5, 6, 7, 8};
        Random random = new Random();
        String telephone = "1";
        for (int i = 0; i < 10; i++) {
            telephone = telephone + arr[random.nextInt(5)] + random.nextInt(9)
                    + "  ****  "
                    + ((random.nextInt(9999 - 1000) + 1000))
                    + (" 刚刚成功提现");
            list.add(telephone);
            telephone = "1";
        }
        return list;
    }

}
