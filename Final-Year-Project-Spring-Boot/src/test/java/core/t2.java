package core;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import com.sun.mail.iap.Argument;

import core.model.PasswordTokenHandler;

public class t2 {

	
	@Test
	public void t1() {
		PasswordTokenHandler pth = Mockito.mock(PasswordTokenHandler.class);
		ArgumentCaptor<String> arg = ArgumentCaptor.forClass(String.class);
		pth.createResetCode("asim1289");
		Mockito.verify(pth).createResetCode(arg.capture());
		assertEquals("asim1289", arg.getValue());
	
	}
}
