package mp;

import mp.config.FieldNameHelper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class MpApplication {

    public static void main(String[] args) throws Exception{
        SpringApplication.run(MpApplication.class, args);
        FieldNameHelper.handler();
    }

    @RequestMapping("/")
    public String hello(){
        return "hello world";
    }



}
