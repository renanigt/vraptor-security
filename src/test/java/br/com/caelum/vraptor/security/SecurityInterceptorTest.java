package br.com.caelum.vraptor.security;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.caelum.vraptor.controller.ControllerMethod;
import br.com.caelum.vraptor.controller.DefaultControllerMethod;
import br.com.caelum.vraptor.interceptor.SimpleInterceptorStack;
import br.com.caelum.vraptor.security.annotation.Public;

public class SecurityInterceptorTest {

	private SecurityInterceptor interceptor;
	
	private @Mock SimpleInterceptorStack stack;
	
	private ControllerMethod walk;
	private ControllerMethod bankAccess;
	private ControllerMethod bark;
	private ControllerMethod eat;
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		
		interceptor = new SecurityInterceptor();

		walk = DefaultControllerMethod.instanceFor(PersonController.class, PersonController.class.getMethod("walk"));
		bankAccess = DefaultControllerMethod.instanceFor(PersonController.class, PersonController.class.getMethod("bankAccess"));
		bark = DefaultControllerMethod.instanceFor(DogController.class, DogController.class.getMethod("bark"));
		eat = DefaultControllerMethod.instanceFor(DogController.class, DogController.class.getMethod("eat"));
	}
	
	static class PersonController {
		@Public
		public void walk() {}

		public void bankAccess() {}
	}
	
	@Public
	static class DogController {
		public void bark() {}
		
		@Public
		public void eat() {}
	}
	
	@Test
	public void shouldNotInterceptWhenMethodWithPublicAnnotationAndClassWithout() {
		assertFalse(interceptor.accepts(walk));
	}
	
	@Test
	public void shouldInterceptWhenMethodAndClassWithoutPublicAnnotation() {
		assertTrue(interceptor.accepts(bankAccess));
	}
	
	@Test
	public void shouldInterceptWhenClassWithPublicAnnotationAndMethodWithout() {
		assertTrue(interceptor.accepts(bark));
	}
	
	@Test
	public void shouldInterceptWhenClassAndMethodWithPublicAnnotation() {
		assertTrue(interceptor.accepts(eat));
	}
	
}
