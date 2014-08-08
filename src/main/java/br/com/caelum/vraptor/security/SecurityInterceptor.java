package br.com.caelum.vraptor.security;

import br.com.caelum.vraptor.Intercepts;
import br.com.caelum.vraptor.interceptor.SimpleInterceptorStack;

@Intercepts
public class SecurityInterceptor {

	public void intercept(SimpleInterceptorStack stack) {
		stack.next();
	}
	
}
