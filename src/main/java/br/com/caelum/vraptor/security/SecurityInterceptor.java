package br.com.caelum.vraptor.security;

<<<<<<< HEAD
import javax.inject.Inject;

=======
>>>>>>> Refactoring.
import br.com.caelum.vraptor.Accepts;
import br.com.caelum.vraptor.Intercepts;
import br.com.caelum.vraptor.controller.ControllerMethod;
import br.com.caelum.vraptor.interceptor.SimpleInterceptorStack;
import br.com.caelum.vraptor.security.annotation.Public;

@Intercepts
public class SecurityInterceptor {

	public void intercept(SimpleInterceptorStack stack) {
		stack.next();
	}

<<<<<<< HEAD
	
	@Accepts
	public boolean accepts() {
		return !method.containsAnnotation(Public.class);
=======
	@Accepts
	public boolean accepts(ControllerMethod method) {
		return !method.containsAnnotation(Public.class) || method.getController().getType().isAnnotationPresent(Public.class);
>>>>>>> Refactoring.
	}
	
}
