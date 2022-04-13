package javaschool.ecare.loader;

import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Log4j2
@Component
public class BeforeLoaderAspect {

    @Before("execution(* javaschool.ecare.loader.LoaderServiceImpl.*(..))")
    public void beforeLoader() {
        log.info("-----> Loader has been started.");
    }

}
