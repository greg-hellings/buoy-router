package buoy.router.http;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Gregory on 4/22/2015.
 */
public class ParameterizedRoute extends buoy.router.SimpleRoute<Verb> {

    private List<Object> parts;
    public ParameterizedRoute(Verb verb, String path) {
        super(verb, path);
        String [] partsArray = path.split("/");
        this.parts = new ArrayList<>(partsArray.length);
        for (String part: partsArray) {
            if (part.startsWith(":")) {
                this.parts.add(new Parameter(part.substring(1)));
            } else if (part.startsWith("\\:")) {
                this.parts.add(part.substring(1));
            } else {
                this.parts.add(part);
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        // Simple tests
        if (this == o) return true;
        if (! (o instanceof buoy.router.Route)) return false;
        buoy.router.Route<Verb> other = (buoy.router.Route<Verb>) o;
        // Verb tests
        if (other.getVerb() != this.getVerb() && this.getVerb() != Verb.ANY && other.getVerb() != Verb.ANY) return false;
        // Path tests
        String[] otherParts = other.getPath().split("/");
        if (otherParts.length > this.parts.size()) return false;
        for (int i = 0; i < this.parts.size(); ++i) {
            Object part = this.parts.get(i);
            if (i >= otherParts.length) {
                if (!(part instanceof Parameter)) return false;
            } else {
                if (!(part instanceof Parameter) && !otherParts[i].equals(part)) return false;
            }
        }
        return true;
    }

    public Map<String, String> getParameters(String path) {
        String[] pathParts = path.split("/");
        Map<String, String> parameters = new HashMap<>();
        for (int i = 0; i < this.parts.size(); ++i) {
            Object part = this.parts.get(i);
            // We don't care about path elements that are not parameters
            if (part instanceof Parameter) {
                Parameter parameter = (Parameter) part;
                String value = null;
                if (i < pathParts.length) {
                    value = pathParts[i];
                }
                parameters.put(parameter.getName(), value);
            }
        }
        return parameters;
    }

    public Map<String, String> getParameters(buoy.router.Route route) {
        return this.getParameters(route.getPath());
    }

    private class Parameter {
        private String name;

        public Parameter(String name) {
            this.name = name;
        }

        public String getName() {
            return this.name;
        }
    }
}
