package model;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Cachable {
    String fileName() default "";

    boolean zip() default true;

    int arraySize() default 0;

    int borderNumber() default 0;
}
