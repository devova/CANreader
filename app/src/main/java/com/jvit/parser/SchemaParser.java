package com.jvit.parser;


import com.jvit.bus.Bus;

import java.io.ByteArrayOutputStream;

public interface SchemaParser {
    Bus parse(ByteArrayOutputStream stream);
}
