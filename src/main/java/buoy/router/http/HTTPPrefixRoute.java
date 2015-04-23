package buoy.router.http;

import buoy.router.PrefixRoute;
import buoy.router.Route;

/**
 * Created by Gregory on 4/20/2015.
 */
public class HTTPPrefixRoute extends PrefixRoute<Verb> {

    public HTTPPrefixRoute(Verb verb, String path) {
        super(verb, path);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Route)) {
            return false;
        }
        Route<Verb> other = null;
        try {
            other = (Route<Verb>) o;
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
