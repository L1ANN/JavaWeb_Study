package util;

import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.UUID;

/**
 * @Author:L1ANN
 * @Description:
 * @Date:Created in 17:17 2017/12/12
 * @Modified By:
 */
public class WebUtils {

    public static <T> T request2Bean(HttpServletRequest request,Class<T> clazz){
        try{
            T bean = clazz.newInstance();
            Map map = request.getParameterMap();

            BeanUtils.populate(bean,map);
            return bean;
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    public static String makeId(){
        return UUID.randomUUID().toString();
    }
}
