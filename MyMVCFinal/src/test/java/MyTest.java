import com.cqu.context.MyApplicationContext;
import org.junit.Test;

public class MyTest {
    @Test
    public void contextTest(){
        MyApplicationContext myApplicationContext=new MyApplicationContext("classpath:MyMVC.xml");
        myApplicationContext.init();
    }
}
