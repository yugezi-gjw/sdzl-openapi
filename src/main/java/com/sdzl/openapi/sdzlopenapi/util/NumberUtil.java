package com.sdzl.openapi.sdzlopenapi.util;

import java.math.BigDecimal;

/**
 * @ClassName MathUtil
 * @Description
 * @Author bhp9696
 * @Date 2018/11/30 11:06
 * @Version 1.0
 */
public class NumberUtil {
    /**
     *
     * @param val
     * @return
     */
    public static Double threeDecimalPlaceRoundOff(Double val){
        if(val == null){
            return 0.0;
        }
        BigDecimal bg = new BigDecimal(val);
        return bg.setScale(3,BigDecimal.ROUND_HALF_UP).doubleValue();
    }
    
    /**
     * 
     * @param val
     * @return
     */
    public static BigDecimal threeBigDecimalPlaceRoundOff(Double val){
      if(val == null){
          return new BigDecimal(0);
      }
      BigDecimal bg = new BigDecimal(val);
      return bg.setScale(3,BigDecimal.ROUND_HALF_UP);
  }
}