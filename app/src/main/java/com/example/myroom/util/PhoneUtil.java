package com.example.myroom.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 判断是否是手机号
 * Created by zjy on 2016/5/9.
 */
public class PhoneUtil {

    public static boolean isPhone(String phone) {
        if (phone == null || phone.length() == 0 || !(phone instanceof String)) {
            return false;
        }
        String str = "^1\\d{10}$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(phone);
        return m.matches();
    }

    //判断是否是正确中文名字
    public static boolean isCHNameWithName(String name) {
        if (name == null || name.length() == 0 || !(name instanceof String)) {
            return false;
        }
        String check = "[\u4E00-\u9FA5]{1,10}·{0,1}•{0,1}[\u4E00-\u9FA5]{1,63}";
//        NSPredicate *predicate = [NSPredicate predicateWithFormat:@"SELF MATCHES%@",check];
        Pattern p = Pattern.compile(check);
        Matcher m = p.matcher(name);
        return m.matches();
//        return [predicate evaluateWithObject:name];
    }

    // 验证银行卡号
    public static boolean isBankCardNo(String cardNo) {
        try {

            if (cardNo == null || cardNo.length() == 0) {
                return false;
            }

            int oddsum = 0;     //奇数求和
            int evensum = 0;    //偶数求和
            int allsum = 0;
            int cardNoLength = cardNo.length();
            int lastNum = Integer.valueOf(cardNo.substring(cardNoLength - 1)).intValue();//[[cardNo substringFromIndex:cardNoLength - 1]intValue];

            cardNo = cardNo.substring(0, cardNoLength - 1);//[cardNo substringToIndex:cardNoLength - 1];
            for (int i = cardNoLength - 1; i >= 1; i--) {
                String tmpString = cardNo.substring(i - 1, i);//[cardNo substringWithRange:NSMakeRange(i - 1, 1)];
                int tmpVal = Integer.valueOf(tmpString).intValue();//[tmpString intValue];
                if (cardNoLength % 2 == 1) {
                    if ((i % 2) == 0) {
                        tmpVal *= 2;
                        if (tmpVal >= 10)
                            tmpVal -= 9;
                        evensum += tmpVal;
                    } else {
                        oddsum += tmpVal;
                    }
                } else {
                    if ((i % 2) == 1) {
                        tmpVal *= 2;
                        if (tmpVal >= 10)
                            tmpVal -= 9;
                        evensum += tmpVal;
                    } else {
                        oddsum += tmpVal;
                    }
                }
            }

            allsum = oddsum + evensum;
            allsum += lastNum;
            if ((allsum % 10) == 0)
                return true;
            else
                return false;

        } catch (Exception e) {
            return false;
        }
    }
}
