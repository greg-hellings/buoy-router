package buoy.router;

/**
 * Created by Gregory on 4/20/2015.
 */
public class PrefixRoute<T extends Enum> extends SimpleRoute<T> {
    private T verb;
    private String path;

    public PrefixRoute(T verb, String path) {
        super(verb, path);
    }

    /**
     * Implements an equality comparison between this route and the matching route. This type of match for a PrefixRoute
     * does not confer symmetry. That is, specifically, if this.equals(other) it does not necessarily follow that
     * other.equals(this). This is because the other might not be a prefix route and might test for the full string, or
     * the other might be a prefix route but its prefix is a strict superset of this one, meaning this one will find
     * equivalence while the other will not.
     *
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        // If it's not even a Route, we definitely aren't equal
        if (! (o instanceof Route)) {
            return false;
        }
        Route<T> other = null;
        // If it's not a route of the same type, then we definitely aren't equal
        try {
            other = (Route<T>) o;
        } catch(ClassCastException exception) {
            return false;
        }
        // If the verbs are not compatible, then we definitely aren't equal
        if (other.getVerb() != this.getVerb()) {
            return false;
        }
        // If the other one's path starts with this as a substring, then this equals that
        if (!other.getPath().startsWith(this.getPath())) {
            return false;
        }
        return true;
    }
}
