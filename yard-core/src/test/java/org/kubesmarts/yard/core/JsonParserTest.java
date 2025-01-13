package org.kubesmarts.yard.core;

import org.drools.util.IoUtils;
import org.junit.jupiter.api.Test;
import org.kubesmarts.yard.api.model.*;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

public class JsonParserTest {

    @Test
    public void testWhenThen() throws Exception {
        String s = new String(IoUtils.readBytesFromInputStream(this.getClass().getResourceAsStream("/insurance-base-price.json"), true));
        YaRD yaRD = new YaRD_JsonMapperImpl().fromJSON(s);

        assertEquals("Base price", yaRD.getElements().get(0).getName());
        final DecisionTable dtable = (DecisionTable) yaRD.getElements().get(0).getLogic();
        assertEquals(1, dtable.getRules().get(0).getRowNumber());
        assertEquals(2, dtable.getRules().get(1).getRowNumber());
        final WhenThenRule rule = (WhenThenRule) dtable.getRules().get(0);
        assertEquals("<21", rule.getWhen().get(0));
        assertEquals(new BigDecimal(800), rule.getThen());
    }

    @Test
    public void testInlineRule() throws Exception {
        String s = new String(IoUtils.readBytesFromInputStream(this.getClass().getResourceAsStream("/simplified-ticket-score.json"), true));
        YaRD yaRD = new YaRD_JsonMapperImpl().fromJSON(s);

        assertEquals("Level", yaRD.getElements().get(0).getName());
        DecisionTable dtable = (DecisionTable) yaRD.getElements().get(0).getLogic();
        assertInstanceOf(InlineRule.class, dtable.getRules().get(0));
    }

}
