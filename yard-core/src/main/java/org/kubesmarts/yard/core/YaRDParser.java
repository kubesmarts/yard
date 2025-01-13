package org.kubesmarts.yard.core;

import org.drools.ruleunits.api.DataSource;
import org.kubesmarts.yard.api.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class YaRDParser {
    static YaRDParser fromModel(final YaRD model) throws IOException {
        return new YaRDParser(model);
    }
    static YaRDParser fromYaml(final String yaml) throws IOException {
        final YaRD model = new YaRD_YamlMapperImpl().read(yaml);
        return new YaRDParser(model);
    }

    static YaRDParser fromYaml(final Reader reader) throws IOException {
        final String text = read(reader);
        return fromYaml(text);
    }

    static YaRDParser fromJson(String json) {
        final YaRD model = new YaRD_JsonMapperImpl().fromJSON(json);
        return new YaRDParser(model);
    }

    private static String read(Reader reader) throws IOException {
        final StringBuilder fileData = new StringBuilder(1000);
        char[] buf = new char[1024];
        int numRead;
        while ((numRead = reader.read(buf)) != -1) {
            String readData = String.valueOf(buf,
                    0,
                    numRead);
            fileData.append(readData);
            buf = new char[1024];
        }
        reader.close();
        return fileData.toString();
    }

    private static final Logger LOG = LoggerFactory.getLogger(YaRDParser.class);
    private final YaRDDefinitions definitions = new YaRDDefinitions(new HashMap<>(), new ArrayList<>(), new HashMap<>());
    private final YaRD model;

    private YaRDParser(final YaRD model) {
        this.model = model;
        appendInputs();
        appendUnits();
    }

    public YaRD getModel() {
        return model;
    }

    public YaRDDefinitions getDefinitions() {
        return definitions;
    }

    private void appendUnits() {
        final List<Element> list = model.getElements();
        final List<String> existingNames = new ArrayList<>();
        for (Element hi : list) {
            final String nameString = hi.getName();
            if (existingNames.contains(nameString)) {
                throw new IllegalArgumentException("Two element definitions with the same name are not allowed.");
            } else {
                existingNames.add(nameString);
            }
            LOG.debug("parsing {}", nameString);
            final Firable decisionLogic = createDecisionLogic(nameString, hi.getLogic());
            definitions.units().add(decisionLogic);
        }
    }

    private Firable createDecisionLogic(String nameString, DecisionLogic decisionLogic) {
        if (decisionLogic instanceof DecisionTable decisionTable) {
            return new SyntheticRuleUnitWrapper(new DTableUnitBuilder(definitions, nameString, decisionTable).build());
        } else if (decisionLogic instanceof LiteralExpression literalExpression) {
            return new LiteralExpressionBuilder(model.getExpressionLang(), definitions, nameString, literalExpression).build();
        } else {
            throw new UnsupportedOperationException("Not implemented.");
        }
    }

    private void appendInputs() {
        final List<Input> list = model.getInputs();
        for (Input hi : list) {
            String nameString = hi.getName();
            @SuppressWarnings("unused")
            Class<?> typeRef = processType(hi.getType());
            definitions.inputs().put(nameString, DataSource.createSingleton());
        }
    }

    private Class<?> processType(String string) {
        switch (string) {
            case "string":
            case "number":
            case "boolean":
            default:
                return Object.class;
        }
    }
}
