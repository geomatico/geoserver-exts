package org.geotools.ysld.parse;

import org.geotools.ysld.YamlObject;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.events.*;
import org.yaml.snakeyaml.reader.UnicodeReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;

/**
 * Base Yaml parsing class.
 */
public class YamlParser {

    Reader yaml;

    public YamlParser(InputStream yaml) {
        this(new UnicodeReader(yaml));
    }

    public YamlParser(Reader yaml) {
        this.yaml = yaml;
    }

    public <T extends YamlParseHandler> T parse(T root) throws IOException {
        Object parsed = new Yaml().load(yaml);

        YamlParseContext context = new YamlParseContext();
        context.push(YamlObject.create(parsed), root);

        while(context.next());

        return root;
    }
}
