package br.com.caelum.vraptor.security;

import br.com.caelum.vraptor.security.rule.SecurityRule;

public class SafeByRuleFalse implements SecurityRule {

	@Override
	public boolean hasPermission() {
		return false;
	}

}
