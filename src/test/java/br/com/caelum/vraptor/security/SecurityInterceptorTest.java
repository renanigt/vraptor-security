package br.com.caelum.vraptor.security;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
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
	public void shouldNotInterceptWhenMethodHasPublicAnnotation() {
		when(method.containsAnnotation(Public.class)).thenReturn(true);
		
		interceptor.intercept(stack);
		
		verify(stack, never()).next();
	}
	
	@Test
	public void shouldInterceptWhenMethodDoesNotHavePublicAnnotation() {
		when(method.containsAnnotation(Public.class)).thenReturn(false);
		
		interceptor.intercept(stack);
		
		verify(stack).next();
	}
	
}
