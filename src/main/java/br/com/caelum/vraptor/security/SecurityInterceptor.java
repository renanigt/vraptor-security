package br.com.caelum.vraptor.security;

import javax.inject.Inject;

import br.com.caelum.vraptor.Accepts;
import br.com.caelum.vraptor.Intercepts;
import br.com.caelum.vraptor.controller.ControllerMethod;
import br.com.caelum.vraptor.interceptor.SimpleInterceptorStack;
import br.com.caelum.vraptor.security.annotation.Public;
import br.com.caelum.vraptor.security.rule.SecurityRule;

@Intercepts
public class SecurityInterceptor {

	private final SecurityRule rule;
	
	/**
	 * @deprecated CDI eyes only.
	 */
	public SecurityInterceptor() {
		this(null);
	}
	
	@Inject
	public SecurityInterceptor(SecurityRule rule) {
		this.rule = rule;
	}
	
	public void intercept(SimpleInterceptorStack stack) {
		if(rule.hasPermission()) {
			stack.next();
		}
	}

	@Accepts
	public boolean accepts(ControllerMethod method) {
		return !method.containsAnnotation(Public.class) || method.getController().getType().isAnnotationPresent(Public.class);
	}
	
}
