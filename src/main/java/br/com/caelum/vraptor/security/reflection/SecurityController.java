package br.com.caelum.vraptor.security.reflection;

import javax.enterprise.inject.Vetoed;

import net.vidageek.mirror.dsl.Mirror;
import br.com.caelum.vraptor.controller.ControllerMethod;
import br.com.caelum.vraptor.security.annotation.SafeBy;
import br.com.caelum.vraptor.security.rule.SecurityRule;

@Vetoed
public class SecurityController {

	private final Class<?> controller;
	
	public SecurityController(ControllerMethod method) {
		this.controller = method.getController().getType();
	}
	
	public boolean hasSafeByAnnotation() {
		return controller.isAnnotationPresent(SafeBy.class);
	}
	
	public SecurityRule instanceForSafeByValue() {
		if(hasSafeByAnnotation()) {
			Class<? extends SecurityRule> clazz = controller.getAnnotation(SafeBy.class).value();
			return new Mirror().on(clazz).invoke().constructor().withoutArgs();
		}
		
		return null;
	}

}
