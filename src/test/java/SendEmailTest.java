import org.junit.Test;

import com.importsource.email.Sendmail;

public class SendEmailTest {
	@Test
   public void send() throws Exception{
		Sendmail sendemail=new Sendmail();
		sendemail.sendText("sdfsdf", "meixixizf24@gmail.com");
	}
}
