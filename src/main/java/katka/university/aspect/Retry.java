package katka.university.aspect;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import static java.lang.annotation.ElementType.METHOD;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(RUNTIME)
@Target({METHOD, TYPE})
public @interface Retry {

  int times() default 3;

  long waitTime() default 1000;
}
