package br.com.caelum.vraptor.security.reflection;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import net.vidageek.mirror.exception.ReflectionProviderException;

import org.junit.Before;
import org.junit.Test;

import br.com.caelum.vraptor.controller.ControllerMethod;
import br.com.caelum.vraptor.controller.DefaultControllerMethod;
import br.com.caelum.vraptor.security.annotation.SafeBy;
import br.com.caelum.vraptor.security.rule.SafeByRuleTrue;

public class SecurityControllerTest {

	private SecurityController securityController;
	private ControllerMethod walk;
	
	@Before
	public void setUp() throws Exception {
		walk = DefaultControllerMethod.instanceFor(PersonController.class, PersonController.class.getMethod("walk"));
		securityController = new SecurityController(walk);
	}

	@SafeBy(SafeByRuleTrue.class)
	static class PersonController {
		public void walk() {};
	}
	
	@Test
	public void shouldReturnTrueWhenControllerHasSafeByAnnotation() {
		assertTrue(securityController.hasSefaByAnnotation());
	}

	@Test
	public void shouldReturnNewInstace() {
		try {
			securityController.instanceForSafeByValue();
		} catch(ReflectionProviderException e) {
			fail("Should not throw an exception.");
		}
	}
	
}
