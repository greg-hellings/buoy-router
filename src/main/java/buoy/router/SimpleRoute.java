package buoy.router;

/**
 * Created by Gregory on 4/21/2015.
 */
public class SimpleRoute<T extends Enum> implements Route<T>{
    private T verb;
    private String path;

    public SimpleRoute(T verb, String path) {
        this.verb = verb;
        this.path = path;
    }
    @Override
    public T getVerb() {
        return this.verb;
    }

    @Override
    public String getPath() {
        return this.path;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Route)) return false;
        Route<T> other = null;
        try {
            other = (Route<T>) o;
        } catch(ClassCastException ex) {
            return false;
        }
        if (other.getVerb() != this.getVerb() || other.getPath() != this.getPath()) return false;
        return true;
    }
}
