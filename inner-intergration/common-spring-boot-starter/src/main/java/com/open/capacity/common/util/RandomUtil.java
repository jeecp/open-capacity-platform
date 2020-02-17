package com.open.capacity.common.util;

import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * 随机数生成util
 **/
public class RandomUtil {

    private static final ThreadLocalRandom random=ThreadLocalRandom.current();

    /**
     * 生成订单编号-方式一
     * @return
     */
    public static String generateOrderCode(){
        //TODO:时间戳+N为随机数流水号
    	
        return  DateTime.now().toString("yyyyMMddHHmmssSS") + generateNumber(4);
    }

    //num为随机数流水号
    public static String generateNumber(final int num){
        StringBuffer sb=new StringBuffer();
        for (int i=1;i<=num;i++){
            sb.append(random.nextInt(9));
        }
        return sb.toString();
        
    }
    
    


    

}