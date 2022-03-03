package katka.university.aspect;

import java.lang.reflect.Method;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class RetryAspect {

  @Pointcut(value = "@annotation(katka.university.aspect.Retry) || @within(katka.university.aspect.Retry)")
  public void retryPointCut() {
  }

  @Around(value = "@annotation(katka.university.aspect.Retry) || @within(katka.university.aspect.Retry)")
  public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
    Retry retry;
    Signature signature = joinPoint.getSignature();
    //retry annotáció példány kinyerése
    // ha metódusra rakták rá, akkor arra kasztolja
    if (signature instanceof MethodSignature methodSignature) {
      Method method = methodSignature.getMethod();
      retry = method.getAnnotation(Retry.class);
    } else { //ha osztályszinten van
      Class<?> declaringType = signature.getDeclaringType();
      retry = declaringType.getAnnotation(Retry.class);
    }
    int times = retry.times();
    long waitTime = retry.waitTime();

    if (times <= 0) {
      times = 1;
    }

    for (int numTry = 1; numTry <= times; numTry++) {

      System.out.format("Try times: %d %n", numTry);

      try {
        return joinPoint.proceed();
      } catch (Exception e) {

        if (numTry == times) {
          throw e;
        }

        if (waitTime > 0) {
          Thread.sleep(waitTime);
        }
      }
    }
    return null;    //soha nem jutunk ide
  }
}
