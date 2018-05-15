package com.dd.mylibrary.utils;

import android.text.TextUtils;
import android.widget.EditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 类描述：验证工具类
 * 作者：lzy on 2016/10/11
 * 邮箱：1556342503@qq.com
 */

public class ValidateUtils {

    /**
     * 判断手机格式是否正确
     *
     * @param mobiles
     * @return 移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
     * 联通：130、131、132、152、155、156、185、186
     * 电信：133、153、180、189、（1349卫通）
     * 总结起来就是第一位必定为1，第二位必定为3、4、5、7、8，其他位置的可以为0-9
     */
    public static boolean isMobileNO(String mobiles) {
        //"[1]"代表第1位为数字1，"[358]"代表第二位可以为3、4、5、7、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        String telRegex = "[1][34578]\\d{9}";
        if (TextUtils.isEmpty(mobiles)) return false;
        else return mobiles.matches(telRegex);
    }

    public static boolean isTruckNum(EditText view) {
        String truckNum = view.getText().toString().trim();
        if (truckNum == null || truckNum.equals("")) {
            return false;
        }
        if (truckNum.length() != 7) {
            return false;
        }

        //车牌号格式验证
        String vehicleNoStyle = "^[\u4e00-\u9fa5]{1}[A-Z0-9]{6}$";
        Pattern pattern = Pattern.compile(vehicleNoStyle);
        Matcher matcher = pattern.matcher(truckNum);
        return matcher.matches();
    }

    /**
     * 方法描述：验证车牌号是否为正确的
     *
     * @param view 编辑框View控件
     * @return
     */
    public static boolean isPlateNo(EditText view) {
        String no = view.getText().toString().trim();
        if (no == null || no.equals("")) {
            return false;
        }
        String str = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        String[] str1 = {"京", "津", "冀", "晋", "蒙", "辽", "吉", "黑", "沪", "苏",
                "浙", "皖", "闽", "赣", "鲁", "豫", "鄂", "湘", "粤", "桂", "琼", "渝",
                "川", "贵", "云", "藏", "陕", "甘", "青", "宁", "新", "农", "台", "中",
                "武", "WJ", "亥", "戌", "酉", "申", "未", "午", "巳", "辰", "卯", "寅",
                "丑", "子", "葵", "壬", "辛", "庚", "己", "戊", "丁", "丙", "乙", "甲",
                "河北", "山西", "北京", "北", "南", "兰", "沈", "济", "成", "广", "海", "空",
                "军", "京V", "使"};

        if (no.equals("新车")) {
            return true;
        }

        if (no.length() == 7) {
            int h = 0;
            for (int r = 0; r < no.length(); r++) {
                if (str.indexOf(no.charAt(r)) != -1) {
                    h++;
                }
            }
            if (h == 7) {
                return true;
            }
        }
        if (no.length() > 1) {

            String jq1 = no.substring(0, 1);
            String jq2 = no.substring(0, 2);

            for (int k = 0; k < str1.length; k++) {
                if (str1[k].equals(jq1)) {
                    if (no.length() <= 8) {
                        return true;
                    }
                }
                if (str1[k].equals(jq2)) {
                    if (no.length() <= 8) {
                        return true;
                    }
                }
            }
        }
        return false;
    }


/*********************************** 身份证验证开始 ****************************************/
    /**
     * 身份证号码验证
     * 1、号码的结构：
     *         公民身份号码是特征组合码，由十七位数字本体码和一位校验码组成。
     *         排列顺序从左至右依次为：六位数字地址码，八位数字出生日期码，三位数字顺序码和一位数字校验码。
     * 2、地址码(前六位数）
     *         表示编码对象常住户口所在县(市、旗、区)的行政区划代码，按GB/T2260的规定执行。
     * 3、出生日期码（第七位至十四位）
     *         表示编码对象出生的年、月、日，按GB/T7408的规定执行，年、月、日代码之间不用分隔符。
     * 4、顺序码（第十五位至十七位）
     *         表示在同一地址码所标识的区域范围内，对同年、同月、同日出生的人编定的顺序号，
     *         顺序码的奇数分配给男性，偶数分配给女性。
     * 5、校验码（第十八位数）
     *    （1）十七位数字本体码加权求和公式 S = Sum(Ai * Wi), i = 0, ... , 16 ，先对前17位数字的权求和
     *             Ai:表示第i位置上的身份证号码数字值
     *             Wi:表示第i位置上的加权因子 Wi: 7 9 10 5 8 4 2 1 6 3 7 9 10 5 8 4 2
     *    （2）计算模 Y = mod(S, 11)
     *    （3）通过模得到对应的校验码 Y: 0 1 2 3 4 5 6 7 8 9 10 校验码: 1 0 X 9 8 7 6 5 4 3 2
     */

    /**
     * 功能：身份证的有效验证
     *
     * @param IDStr 身份证号
     * @return 有效：返回"" 无效：返回String信息
     * @throws ParseException
     */
    public static final String SUCCESS = "有效的身份证";

    @SuppressWarnings("unchecked")
    public static String IDCardValidate(String IDStr) throws ParseException {
        String errorInfo = "";// 记录错误信息
        String[] ValCodeArr = {"1", "0", "x", "9", "8", "7", "6", "5", "4",
                "3", "2"};
        String[] Wi = {"7", "9", "10", "5", "8", "4", "2", "1", "6", "3", "7",
                "9", "10", "5", "8", "4", "2"};
        String Ai = "";
        // ================ 号码的长度 15位或18位 ================
        if (IDStr.length() != 15 && IDStr.length() != 18) {
            errorInfo = "身份证号码长度应该为15位或18位";
            return errorInfo;
        }
        // =======================(end)========================

        // ================ 数字 除最后以为都为数字 ================
        if (IDStr.length() == 18) {
            Ai = IDStr.substring(0, 17);
        } else if (IDStr.length() == 15) {
            Ai = IDStr.substring(0, 6) + "19" + IDStr.substring(6, 15);
        }
        if (!isNumeric(Ai)) {
            errorInfo = "身份证15位号码都应为数字 ; 18位号码除最后一位外，都应为数字";
            return errorInfo;
        }
        // =======================(end)========================

        // ================ 出生年月是否有效 ================
        String strYear = Ai.substring(6, 10);// 年份
        String strMonth = Ai.substring(10, 12);// 月份
        String strDay = Ai.substring(12, 14);// 月份
        if (!isDate(strYear + "-" + strMonth + "-" + strDay)) {
            errorInfo = "身份证生日无效";
            return errorInfo;
        }
        GregorianCalendar gc = new GregorianCalendar();
        SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        try {
            if ((gc.get(Calendar.YEAR) - Integer.parseInt(strYear)) > 150
                    || (gc.getTime().getTime() - s.parse(
                    strYear + "-" + strMonth + "-" + strDay).getTime()) < 0) {
                errorInfo = "身份证生日不在有效范围";
                return errorInfo;
            }
        } catch (NumberFormatException | ParseException e) {
            e.printStackTrace();
        }
        if (Integer.parseInt(strMonth) > 12 || Integer.parseInt(strMonth) == 0) {
            errorInfo = "身份证月份无效";
            return errorInfo;
        }
        if (Integer.parseInt(strDay) > 31 || Integer.parseInt(strDay) == 0) {
            errorInfo = "身份证日期无效";
            return errorInfo;
        }
        // =====================(end)=====================


        // ================ 判断最后一位的值 ================
        int TotalmulAiWi = 0;
        for (int i = 0; i < 17; i++) {
            TotalmulAiWi = TotalmulAiWi
                    + Integer.parseInt(String.valueOf(Ai.charAt(i)))
                    * Integer.parseInt(Wi[i]);
        }
        int modValue = TotalmulAiWi % 11;
        String strVerifyCode = ValCodeArr[modValue];
        Ai = Ai + strVerifyCode;

        if (IDStr.length() == 18) {
            if (!Ai.equals(IDStr)) {
                errorInfo = "身份证号码不合法";
                return errorInfo;
            }
        } else {
            return SUCCESS;
        }
        // =====================(end)=====================
        return SUCCESS;
    }


    /**
     * 功能：判断字符串是否为数字
     *
     * @param str
     * @return
     */
    private static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        return isNum.matches();
    }

    /**
     * 功能：判断字符串是否为日期格式
     *
     * @param
     * @return
     */
    public static boolean isDate(String strDate) {
        Pattern pattern = Pattern
                .compile("^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))(\\s(((0?[0-9])|([1-2][0-3]))\\:([0-5]?[0-9])((\\s)|(\\:([0-5]?[0-9])))))?$");
        Matcher m = pattern.matcher(strDate);
        return m.matches();
    }


    /*********************************** 身份证验证结束 ****************************************/
    public static boolean matcherRealName(String value) {
        String regex = "^([\\u4e00-\\u9fa5]+|([a-zA-Z]+\\s?)+)$";
        return testRegex(regex, value);
    }

    public static boolean matcherPhoneNum(String value) {
        String regex = "^(\\+?\\d{2}-?)?(1[0-9])\\d{9}$";
        return testRegex(regex, value);
    }

    public static boolean matcherAccount(String value) {
        String regex = "[\\u4e00-\\u9fa5a-zA-Z0-9\\-]{4,20}";
        return testRegex(regex, value);
    }

    public static boolean matcherPassword(String value) {
        String regex = "^[a-zA-Z0-9]{6,12}$";
        return testRegex(regex, value);
    }

    public static boolean matcherPassword2(String value) {
        String regex = "(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,}";
        return testRegex(regex, value);
    }


    public static boolean matcherEmail(String value) {
//      String regex = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)" +
//                "+[a-zA-Z]{2,}$";
        String regex = "^[a-z0-9!#$%&'*+/=?^_`{|}~-]+" +
                "(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+" +
                "[a-z0-9](?:[a-z0-9-]*[a-z0-9])?$";
        return testRegex(regex, value);
    }

    public static boolean matcherIP(String value) {
        String regex = "\\b((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\" +
                "d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\" +
                "d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\b";
        return testRegex(regex, value.toLowerCase());
    }

    public static boolean matcherUrl(String value) {
        //String regex = "^(([hH][tT]{2}[pP][sS]?)|([fF][tT][pP]))\\:\\/\\/[wW]{3}\\.[\\w-]+\\.\\w{2,4}(\\/.*)?$";
        String regex = "^(([hH][tT]{2}[pP][sS]?)|([fF][tT][pP]))\\:\\/\\/[\\w-]+\\.\\w{2,4}(\\/.*)?$";
        return testRegex(regex, value.toLowerCase());
    }

    public static boolean testRegex(String regex, String inputValue) {
        return Pattern.compile(regex).matcher(inputValue).matches();
    }

    public static boolean checkPostcode(String postcode) {
        String regex = "[1-9]\\d{5}";
        return Pattern.matches(regex, postcode);
    }

    /**
     * 判断字符串是否为数字
     * @param str
     * @return
     */
    public static boolean isNumber(String str){
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()){
            return false;
        }
        return true;
    }
}


