package br.com.caelum.vraptor.security;

import javax.inject.Inject;

import br.com.caelum.vraptor.Accepts;
import br.com.caelum.vraptor.Intercepts;
import br.com.caelum.vraptor.controller.ControllerMethod;
import br.com.caelum.vraptor.interceptor.SimpleInterceptorStack;
import br.com.caelum.vraptor.security.annotation.DefaultRule;
import br.com.caelum.vraptor.security.annotation.Public;
import br.com.caelum.vraptor.security.annotation.SafeBy;
import br.com.caelum.vraptor.security.reflection.SecurityController;
import br.com.caelum.vraptor.security.reflection.SecurityMethod;
import br.com.caelum.vraptor.security.rule.SecurityRule;

@Intercepts
public class SecurityInterceptor {

	private final SecurityRule defaultRule;
	
	/**
	 * @deprecated CDI eyes only.
	 */
	protected SecurityInterceptor() {
		this(null);
	}
	
	@Inject
	public SecurityInterceptor(@DefaultRule SecurityRule defaultRule) {
		this.defaultRule = defaultRule;
	}
	
	public void intercept(SimpleInterceptorStack stack, ControllerMethod method) {
		SecurityMethod securityMethod = new SecurityMethod(method);
		SecurityController securityController = new SecurityController(method);
		
		if(securityMethod.hasSefaByAnnotation()) {
			SecurityRule safeByRule = securityMethod.instanceForSafeByValue();
			
			if(safeByRule.hasPermission()) {
				stack.next();
			}
		} else if(securityController.hasSefaByAnnotation()) {
			SecurityRule safeByRule = securityController.instanceForSafeByValue();
			
			if(safeByRule.hasPermission()) {
				stack.next();
			}
		} else if(defaultRule.hasPermission()) {
			stack.next();
		}
	}

	@Accepts
	public boolean accepts(ControllerMethod method) {
		return (method.containsAnnotation(SafeBy.class)) 
				|| (!method.getController().getType().isAnnotationPresent(Public.class) 
				&& !method.containsAnnotation(Public.class));
	}
	
}
