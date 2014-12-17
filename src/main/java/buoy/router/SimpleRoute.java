/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package buoy.router;

/**
 * A simple route which requires nothing other than a verb
 * and a path to define itself.
 * 
 * @author greg
 */
public class SimpleRoute implements Route {
    private Verb verb;
    private String path;
    
    public SimpleRoute(Verb verb, String path) {
        this.verb = verb;
        this.path = path;
    }
    
    @Override
    public Verb getVerb() {
        return this.verb;
    }

    @Override
    public String getPath() {
        return this.path;
    }
 
    @Override
    public boolean equals(Object other) {
        if (! (other instanceof Route) ) {
            return false;
        }
        Route route = (Route) other;
        return this.getVerb() == route.getVerb() &&
               this.getPath() == route.getPath();
    }
}
