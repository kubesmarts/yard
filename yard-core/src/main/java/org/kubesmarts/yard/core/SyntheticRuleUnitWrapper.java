package org.kubesmarts.yard.core;

import java.util.Map;

import org.drools.ruleunits.api.RuleUnitInstance;
import org.drools.ruleunits.api.RuleUnitProvider;
import org.drools.ruleunits.dsl.SyntheticRuleUnit;

public class SyntheticRuleUnitWrapper implements Firable {

    private final SyntheticRuleUnit wrapped;

    public SyntheticRuleUnitWrapper(SyntheticRuleUnit wrapped) {
        this.wrapped = wrapped;
    }

    @Override
    public int fire(Map<String, Object> context, YaRDDefinitions units) {
        RuleUnitInstance<SyntheticRuleUnit> unitInstance = RuleUnitProvider.get().createRuleUnitInstance(wrapped);
        int fire = unitInstance.fire();
        RuleUnitProvider.get().invalidateRuleUnits(wrapped.getClass());
        return fire;
    }
}
