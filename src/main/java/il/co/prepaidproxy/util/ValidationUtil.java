package il.co.prepaidproxy.util;

import java.util.Random;

/**
 * Created by tomerpaz on 6/25/17.
 */
public class ValidationUtil {

    public static boolean validateNumeric(String value){
        try{
            Long.parseLong(value);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    //----------------------------------------------------------------------------------
    public static String stringGenerator() {
        char[] chars = "0123456789".toCharArray();
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 12; i++) {
            char c = chars[random.nextInt(chars.length)];
            sb.append(c);
        }
        String output = sb.toString();
        return output;
    }
}
