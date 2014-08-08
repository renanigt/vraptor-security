package br.com.caelum.vraptor.security;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.caelum.vraptor.controller.ControllerMethod;
import br.com.caelum.vraptor.interceptor.SimpleInterceptorStack;
import br.com.caelum.vraptor.security.annotation.Public;

public class SecurityInterceptorTest {

	private SecurityInterceptor interceptor;
	
	private @Mock SimpleInterceptorStack stack;
	private @Mock ControllerMethod method;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		interceptor = new SecurityInterceptor(method);
	}
	
	@Test
	public void shouldNotInterceptWhenMethodWithPublicAnnotation() {
		when(method.containsAnnotation(Public.class)).thenReturn(true);
		assertFalse(interceptor.accepts());
	}
	
	@Test
	public void shouldInterceptWhenMethodWithoutPublicAnnotation() {
		when(method.containsAnnotation(Public.class)).thenReturn(false);
		assertTrue(interceptor.accepts());
	}
	
}
