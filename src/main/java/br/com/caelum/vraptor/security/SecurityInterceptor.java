package br.com.caelum.vraptor.security;

import javax.inject.Inject;

import br.com.caelum.vraptor.Accepts;
import br.com.caelum.vraptor.Intercepts;
import br.com.caelum.vraptor.controller.ControllerMethod;
import br.com.caelum.vraptor.interceptor.SimpleInterceptorStack;
import br.com.caelum.vraptor.security.annotation.Public;

@Intercepts
public class SecurityInterceptor {

	private final ControllerMethod method;
	
	/**
	 * @deprecated CDI eyes only
	 */
	public SecurityInterceptor() {
		this(null);
	}

	@Inject
	public SecurityInterceptor(ControllerMethod method) {
		this.method = method;
	}
	
	public void intercept(SimpleInterceptorStack stack) {
		stack.next();
	}

	
	@Accepts
	public boolean accepts() {
		return !method.containsAnnotation(Public.class);
	}
	
}
