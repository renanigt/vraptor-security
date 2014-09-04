package br.com.caelum.vraptor.security.reflection;

import javax.enterprise.inject.Vetoed;

import net.vidageek.mirror.dsl.Mirror;
import br.com.caelum.vraptor.controller.ControllerMethod;
import br.com.caelum.vraptor.security.annotation.SafeBy;
import br.com.caelum.vraptor.security.rule.SecurityRule;

@Vetoed
public class SecurityMethod {

	private final ControllerMethod method;
	
	public SecurityMethod(ControllerMethod method) {
		this.method = method;
	}
	
	public boolean hasSefaByAnnotation() {
		return method.containsAnnotation(SafeBy.class);
	}
	
	public SecurityRule instanceForSafeByValue() {
		Class<? extends SecurityRule> clazz = method.getMethod().getAnnotation(SafeBy.class).value();
		return new Mirror().on(clazz).invoke().constructor().withoutArgs();
	}
	
}
