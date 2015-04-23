package buoy.router.http;

/**
 * Created by Gregory on 4/20/2015.
 */
public class PrefixRoute extends buoy.router.PrefixRoute<Verb> {

    public PrefixRoute(Verb verb, String path) {
        super(verb, path);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof buoy.router.Route)) {
            return false;
        }
        buoy.router.Route<Verb> other = null;
        try {
            other = (buoy.router.Route<Verb>) o;
        } catch(ClassCastException ex) {
            return false;
        }
        if (other.getVerb() != this.getVerb() && this.getVerb() != Verb.ANY) {
            return false;
        }
        if (!other.getPath().startsWith(this.getPath())) {
            return false;
        }
        return true;
    }
}
