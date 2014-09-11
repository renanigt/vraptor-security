package br.com.caelum.vraptor.security;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.caelum.vraptor.controller.ControllerMethod;
import br.com.caelum.vraptor.controller.DefaultControllerMethod;
import br.com.caelum.vraptor.interceptor.SimpleInterceptorStack;
import br.com.caelum.vraptor.security.annotation.Public;
import br.com.caelum.vraptor.security.annotation.SafeBy;
import br.com.caelum.vraptor.security.rule.SafeByRuleFalse;
import br.com.caelum.vraptor.security.rule.SafeByRuleTrue;
import br.com.caelum.vraptor.security.rule.SecurityRule;

public class SecurityInterceptorTest {

	private SecurityInterceptor interceptor;
	
	private @Mock SimpleInterceptorStack stack;
	private @Mock SecurityRule rule;
	private @Mock SecurityRule defaultRule;
	
	private ControllerMethod walk;
	private ControllerMethod emailAccess;
	private ControllerMethod safePerson;
	private ControllerMethod bark;
	private ControllerMethod eat;
	private ControllerMethod safeDog;
	private ControllerMethod open;
	private ControllerMethod payment;
	private ControllerMethod safeBank;
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		
		interceptor = new SecurityInterceptor(rule, defaultRule);

		walk = DefaultControllerMethod.instanceFor(PersonController.class, PersonController.class.getMethod("walk"));
		emailAccess = DefaultControllerMethod.instanceFor(PersonController.class, PersonController.class.getMethod("emailAccess"));
		safePerson = DefaultControllerMethod.instanceFor(PersonController.class, PersonController.class.getMethod("safePerson"));
		bark = DefaultControllerMethod.instanceFor(DogController.class, DogController.class.getMethod("bark"));
		eat = DefaultControllerMethod.instanceFor(DogController.class, DogController.class.getMethod("eat"));
		safeDog = DefaultControllerMethod.instanceFor(DogController.class, DogController.class.getMethod("safeDog"));
		open = DefaultControllerMethod.instanceFor(BankController.class, BankController.class.getMethod("open"));
		payment = DefaultControllerMethod.instanceFor(BankController.class, BankController.class.getMethod("payment"));
		safeBank = DefaultControllerMethod.instanceFor(BankController.class, BankController.class.getMethod("safeBank"));
	}
	
	static class PersonController {
		@Public
		public void walk() {}

		public void emailAccess() {}

		@SafeBy(SafeByRuleTrue.class)
		public void safePerson() {}
	}
	
	@Public
	static class DogController {
		public void bark() {}
		
		@Public
		public void eat() {}

		@SafeBy(SafeByRuleFalse.class)
		public void safeDog() {}
	}

	@SafeBy(SecurityRule.class)
	static class BankController {
		public void open() {}
		
		@Public
		public void payment() {}

		@SafeBy(SecurityRule.class)
		public void safeBank() {}
	}
	
	@Test
	public void shouldNotInterceptWhenMethodWithPublicAnnotationAndClassWithout() {
		assertFalse(interceptor.accepts(walk));
	}
	
	@Test
	public void shouldInterceptWhenMethodAndClassWithoutPublicAnnotation() {
		assertTrue(interceptor.accepts(emailAccess));
	}

	@Test
	public void shouldInterceptWhenMethodWithSafeByAnnotationAndClassWithout() {
		assertTrue(interceptor.accepts(safePerson));
	}
	
	@Test
	public void shouldNotInterceptWhenClassWithPublicAnnotationAndMethodWithout() {
		assertFalse(interceptor.accepts(bark));
	}
	
	@Test
	public void shouldNotInterceptWhenClassAndMethodWithPublicAnnotation() {
		assertFalse(interceptor.accepts(eat));
	}

	@Test
	public void shouldInterceptWhenMethodWithSafeByAnnotationAndClassWithPublic() {
		assertTrue(interceptor.accepts(safeDog));
	}
	
	@Test
	public void shouldInterceptWhenClassWithSafeByAnnotationAndMethodWithout() {
		assertTrue(interceptor.accepts(open));
	}

	@Test
	public void shouldNotInterceptWhenClassWithSafeByAnnotationAndMethodWithPublic() {
		assertFalse(interceptor.accepts(payment));
	}
	
	@Test
	public void shouldInterceptWhenClassAndMethodWithSafeByAnnotationt() {
		assertTrue(interceptor.accepts(safeBank));
	}
	
	@Test
	public void shouldCallNextMethodWhenUsingDefaultRuleAndItReturnsHasParmissionTrue() {
		when(rule.hasPermission()).thenReturn(true);
		
		interceptor.intercept(stack, emailAccess);
		
		verify(stack).next();
	}

	@Test
	public void shouldNotCallNextMethodWhenUsingDefaultRuleAndItReturnsHasParmissionFalse() {
		when(rule.hasPermission()).thenReturn(false);
		
		interceptor.intercept(stack, emailAccess);
		
		verify(stack, never()).next();
	}
	
	@Test
	public void shouldCallNextMethodWhenUsingSafeByRuleAndItReturnsHasPermissionTrue() {
		interceptor.intercept(stack, safePerson);
		
		verify(stack).next();
	}

	@Test
	public void shouldNotCallNextMethodWhenUsingSafeByRuleAndItReturnsHasPermissionFalse() {
		interceptor.intercept(stack, safeDog);
		
		verify(stack, never()).next();
	}
	
}
