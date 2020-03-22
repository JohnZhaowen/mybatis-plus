package mp.config;

import mp.entity.User;
import mp.enums.FieldNameSpecifies;
import mp.enums.FieldNameSpecify;

import java.lang.reflect.Field;

public class FieldNameHelper {

   public static void handler() throws Exception{


       Field dateField = User.class.getDeclaredField("date");
       FieldNameSpecifies annotation = dateField.getAnnotation(FieldNameSpecifies.class);
       FieldNameSpecify[] values = annotation.value();
       for(FieldNameSpecify f : values){
           System.out.println(f.value());
       }

   }
}
