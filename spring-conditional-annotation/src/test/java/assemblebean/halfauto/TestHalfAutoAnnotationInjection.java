package assemblebean.halfauto;

import com.prayerlaputa.assemblebean.halfauto.HalfAutoUserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author chenglong.yu
 * created on 2020/11/13
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:halfautoannotation.xml"})
public class TestHalfAutoAnnotationInjection {

    @Autowired
    private HalfAutoUserService halfAutoUserService;

    @Test
    public void testInjection() {
        /*
        TODO 总感觉此处使用半自动注入的方式还是有点问题，需要回头查查
         */
        halfAutoUserService.printService();
    }

}
