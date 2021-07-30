package cn.goat.oms.uitls;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Random;

/**
* @Description:随机数工具类
* @author: heyz
* @Date: 2021/7/29 15:57
*/
public class RandomUtil {

    /**
    * @Description:雪花算法获取id
    * @param: []
    * @return: long
    * @author: heyz
    * @Date: 2021/7/29 15:57
    */
    public static long snowFlake(){
        Snowflake snowflake = IdUtil.getSnowflake(1, 1);
        return snowflake.nextId();
    }

    /**
    * @Description:获取订单号
    * @param: []
    * @return: java.lang.String
    * @author: heyz
    * @Date: 2021/7/29 15:57
    */
    public static String getOrderNum(){
        //获取毫秒数
        Long milliSecond = LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli();
        //时间戳+4位随机
        Random random = new Random();
        int i = random.nextInt(4);
        String orderNumber = "" + milliSecond + i;
        return orderNumber;
    }

}
