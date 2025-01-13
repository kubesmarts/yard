package org.kubesmarts.yard.core;

import org.kubesmarts.yard.api.model.LiteralExpression;

import java.util.Objects;

public class LiteralExpressionBuilder {

    private final String expressionLang;
    private final YaRDDefinitions definitions;
    private final String name;
    private final LiteralExpression decisionLogic;

    public LiteralExpressionBuilder(final String expressionLang,
                                    final YaRDDefinitions definitions,
                                    final String name,
                                    final LiteralExpression decisionLogic) {
        this.expressionLang = expressionLang;
        this.definitions = definitions;
        this.name = name;
        this.decisionLogic = decisionLogic;
    }

    public Firable build() {
        final String expr = decisionLogic.getExpression();
        definitions.outputs().put(name, StoreHandle.empty(Object.class));
        if(Objects.equals(expressionLang, "jshell")){
            return new JShellLiteralExpressionInterpreter(name, QuotedExprParsed.from(expr));
        }
        else {
            return new MVELLiteralExpressionInterpreter(name,QuotedExprParsed.from(expr));
        }
    }
}
