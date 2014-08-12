package br.com.caelum.vraptor.security.rule;

import br.com.caelum.vraptor.security.rule.SecurityRule;

public class SafeByRuleTrue implements SecurityRule {

	@Override
	public boolean hasPermission() {
		return true;
	}

}
