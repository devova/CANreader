package com.jwit.parser;


import com.jwit.bus.Bus;

import java.io.ByteArrayOutputStream;

public interface SchemaParser {
    Bus parse(ByteArrayOutputStream stream);
}
