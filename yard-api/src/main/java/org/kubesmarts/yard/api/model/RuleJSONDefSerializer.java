package org.kubesmarts.yard.api.model;

import jakarta.json.*;
import jakarta.json.bind.serializer.DeserializationContext;
import jakarta.json.bind.serializer.JsonbDeserializer;
import jakarta.json.bind.serializer.JsonbSerializer;
import jakarta.json.bind.serializer.SerializationContext;
import jakarta.json.stream.JsonGenerator;
import jakarta.json.stream.JsonParser;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class RuleJSONDefSerializer
        implements JsonbSerializer<Rule>,
        JsonbDeserializer<Rule> {

    private int rowNumber = 1;

    @Override
    public Rule deserialize(JsonParser parser, DeserializationContext deserializationContext, Type type) {

        final JsonValue value = parser.getValue();
        if (value instanceof JsonObject object) {
            final JsonValue when = object.get("when");
            final WhenThenRule whenThenRule = new WhenThenRule(rowNumber++);
            whenThenRule.setWhen(getItems(when));
            final JsonValue then = value.asJsonObject().get("then");
            whenThenRule.setThen(getItem(then));
            return whenThenRule;
        } else if (value instanceof JsonArray array) {
            return new InlineRule(rowNumber++, getItems(array));
        } else {
            throw new IllegalArgumentException("Unknown rule format.");
        }
    }

    @Override
    public void serialize(Rule o, JsonGenerator jsonGenerator, SerializationContext serializationContext) {
        // Not needed, we never serialize.
    }

    private List<Comparable> getItems(final JsonValue jsonValue) {
        final List<Comparable> result = new ArrayList<>();
        if (jsonValue instanceof JsonArray array) {
            for (JsonValue item : array) {
                result.add(getItem(item));
            }
        } else {
            result.add(getItem(jsonValue));
        }
        return result;
    }

    private Comparable getItem(JsonValue jsonValue) {
        if (jsonValue instanceof JsonNumber number) {
            return number.bigDecimalValue();
        } else if (jsonValue.getValueType() == JsonValue.ValueType.FALSE) {
            return false;
        } else if (jsonValue.getValueType() == JsonValue.ValueType.TRUE) {
            return true;
        } else if (jsonValue instanceof JsonString string) {
            return string.getString();
        } else {
            return jsonValue.toString();
        }
    }

}
