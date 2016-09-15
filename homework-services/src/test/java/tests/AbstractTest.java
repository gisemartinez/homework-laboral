package tests;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;



@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration ("file:src/main/resources/spring/spring-webcontext.xml")
public abstract class AbstractTest {
	
	protected final Logger LOGGER = LogManager.getLogger(this.getClass());
}
