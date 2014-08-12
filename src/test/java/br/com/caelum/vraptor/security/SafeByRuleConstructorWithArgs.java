package br.com.caelum.vraptor.security;

import br.com.caelum.vraptor.security.rule.SecurityRule;

public class SafeByRuleConstructorWithArgs implements SecurityRule {

	private boolean value;
	
	public SafeByRuleConstructorWithArgs(boolean value) {
		this.value = value;
	}
	
	@Override
	public boolean hasPermission() {
		return value;
	}

}
