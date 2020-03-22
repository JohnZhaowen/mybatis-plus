//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package mp.enums;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
@Repeatable(FieldNameSpecifies.class)
public @interface FieldNameSpecify {

    String value() default "";

    String tag() default "";

}
