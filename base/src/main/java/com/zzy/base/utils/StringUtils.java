package com.zzy.base.utils;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.RelativeSizeSpan;

import java.text.DecimalFormat;

/**
 * 项目名称: PlusOneLivePush
 * 创建人: 周正一
 * 创建时间：2017/7/28
 */

public class StringUtils {

    /**
     * desc:将数组转为16进制
     *
     * @param bArray
     * @return modified:
     */
    @Nullable
    public static String bytesToHexString(@Nullable byte[] bArray) {
        if (bArray == null) {
            return null;
        }
        if (bArray.length == 0) {
            return "";
        }
        StringBuffer sb = new StringBuffer(bArray.length);
        String sTemp;
        for (int i = 0; i < bArray.length; i++) {
            sTemp = Integer.toHexString(0xFF & bArray[i]);
            if (sTemp.length() < 2)
                sb.append(0);
            sb.append(sTemp.toUpperCase());
        }
        return sb.toString();
    }

    /**
     * desc:将16进制的数据转为数组
     * <p>创建人：聂旭阳 , 2014-5-25 上午11:08:33</p>
     *
     * @param data
     * @return modified:
     */
    public static byte[] stringToBytes(@NonNull String data) {
        String hexString = data.toUpperCase().trim();
        if (hexString.length() % 2 != 0) {
            return null;
        }
        byte[] retData = new byte[hexString.length() / 2];
        for (int i = 0; i < hexString.length(); i++) {
            int int_ch;  // 两位16进制数转化后的10进制数
            char hex_char1 = hexString.charAt(i); ////两位16进制数中的第一位(高位*16)
            int int_ch1;
            if (hex_char1 >= '0' && hex_char1 <= '9')
                int_ch1 = (hex_char1 - 48) * 16;   //// 0 的Ascll - 48
            else if (hex_char1 >= 'A' && hex_char1 <= 'F')
                int_ch1 = (hex_char1 - 55) * 16; //// A 的Ascll - 65
            else
                return null;
            i++;
            char hex_char2 = hexString.charAt(i); ///两位16进制数中的第二位(低位)
            int int_ch2;
            if (hex_char2 >= '0' && hex_char2 <= '9')
                int_ch2 = (hex_char2 - 48); //// 0 的Ascll - 48
            else if (hex_char2 >= 'A' && hex_char2 <= 'F')
                int_ch2 = hex_char2 - 55; //// A 的Ascll - 65
            else
                return null;
            int_ch = int_ch1 + int_ch2;
            retData[i / 2] = (byte) int_ch;//将转化后的数放入Byte里
        }
        return retData;
    }

    /**
     * 拼接两个字符串，不判断是否为空
     * */
    public static String strCompound(@NonNull String... str){
        StringBuilder sb=new StringBuilder();
        for(int i=0;i<str.length;i++){
            sb.append(str[i]);
        }
        return  sb.toString();
    }

    /**
     * 将价格格式化为带人民币符号
     */
    @NonNull
    public static SpannableStringBuilder formatMoney(String price) {
        String str=parseStringPattern2(price, 2);
        return resizeStr(addRmbTag(str),2);
    }

    /**
     * 添加人民币符号
     * */
    public static String addRmbTag(String price){
        String moneyStr = "¥%s";
        return String.format(moneyStr, price);
    }

    /**
     * 金额字符串的小数点后缩小
     *
     * @param str      字符串
     * @param resizeNumber 需要缩小的部分字符串长度
     * @return SpannableStringBuilder
     */
    @NonNull
    public static SpannableStringBuilder resizeStr(@NonNull String str, int resizeNumber) {
        int fstart = str.length()-resizeNumber;
        int fend = str.length();
        SpannableStringBuilder style = new SpannableStringBuilder(str);
        style.setSpan(new RelativeSizeSpan(0.7f), fstart, fend, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        return style;
    }

    /**
     * 按千位分割格式格式化数字
     *
     * @param text  原数字
     * @param scale 小数点保留位数
     * @return
     */
    public static String parseStringPattern2(String text, int scale) {
        if (text == null || "".equals(text) || "null".equals(text)) {
            return parseStringPattern2("0", scale);
        }
        if (text.contains(",") || text.contains("，")) {
            return text;
        }
        String temp = "###,###,###,###,###,###,###,##0";
        if (scale > 0)
            temp += ".";
        for (int i = 0; i < scale; i++)
            temp += "0";
        DecimalFormat format = new DecimalFormat(temp);
        Double d = getDoubleFromStr(text);
        return format.format(d).toString();
    }

    /**
     * String类型转换为double类型
     *
     * @param str
     * @return
     */
    public static double getDoubleFromStr(String str) {
        double d = 0;
        try {
            d = Double.valueOf(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return d;
    }

    /**
     * 银行卡替换，保留后四位
     *
     * 如果银行卡号为空 或者 null ,返回null ；否则，返回替换后的字符串；
     *
     * @param bankCard 银行卡号
     * @return
     */
    public static String bankCardReplaceWithStar(String bankCard) {
        if (bankCard == null || bankCard.isEmpty() ) {
            return "";
        } else {
            return bankCard.replaceAll("(?<=\\d{0})\\d(?=\\d{4})","*");
        }
    }
}
