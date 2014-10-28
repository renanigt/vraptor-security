package br.com.caelum.vraptor.security.reflection;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import net.vidageek.mirror.exception.ReflectionProviderException;

import org.junit.Before;
import org.junit.Test;

import br.com.caelum.vraptor.controller.ControllerMethod;
import br.com.caelum.vraptor.controller.DefaultControllerMethod;
import br.com.caelum.vraptor.security.annotation.SafeBy;
import br.com.caelum.vraptor.security.rule.SafeByRuleConstructorWithArgs;
import br.com.caelum.vraptor.security.rule.SafeByRuleTrue;

public class SecurityMethodTest {

	private SecurityMethod securityMethod;
	private ControllerMethod walk;
	private ControllerMethod walkException;
	private ControllerMethod walkWithoutAnnotation;
	
	@Before
	public void setUp() throws Exception {
		walk = DefaultControllerMethod.instanceFor(PersonController.class, PersonController.class.getMethod("walk"));
		walkException = DefaultControllerMethod.instanceFor(PersonController.class, PersonController.class.getMethod("walkException"));
		walkWithoutAnnotation = DefaultControllerMethod.instanceFor(PersonController.class, PersonController.class.getMethod("walkWithoutAnnotation"));
		securityMethod = new SecurityMethod(walk);
	}

	static class PersonController {
		@SafeBy(SafeByRuleTrue.class)
		public void walk() {};

		@SafeBy(SafeByRuleConstructorWithArgs.class)
		public void walkException() {};

		public void walkWithoutAnnotation() {};
	}
	
	@Test
	public void shouldReturnTrueWhenMethodHasSafeByAnnotation() {
		assertTrue(securityMethod.hasSafeByAnnotation());
	}

	@Test
	public void shouldReturnFalseWhenMethodDoesNotHaveSafeByAnnotation() {
		securityMethod = new SecurityMethod(walkWithoutAnnotation);
		assertFalse(securityMethod.hasSafeByAnnotation());
	}
	
	@Test
	public void shouldReturnNewInstace() {
		try {
			securityMethod.instanceForSafeByValue();
		} catch(ReflectionProviderException e) {
			fail("Should not throw an exception.");
		}
	}
	
	@Test
	public void shouldThrowExceptionWhenCanNotReturnNewInstance() {
		securityMethod = new SecurityMethod(walkException);
		
		try {
			securityMethod.instanceForSafeByValue();
			fail("Should throw an exception.");
		} catch(Exception e) {
			
		}
	}
	
}
