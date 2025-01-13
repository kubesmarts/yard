package org.kubesmarts.yard.core;

import java.util.Map;

public interface Firable {

    int fire(Map<String, Object> context, YaRDDefinitions units);
}