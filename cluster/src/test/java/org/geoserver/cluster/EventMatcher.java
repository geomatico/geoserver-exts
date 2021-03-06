package org.geoserver.cluster;

import org.easymock.EasyMock;
import org.easymock.IArgumentMatcher;

public class EventMatcher implements IArgumentMatcher {
    
    Object source; 
    
    public EventMatcher(Object source) {
        super();
        this.source = source;
    }

    static public Event event(Object source) {
        EasyMock.reportMatcher(new EventMatcher(source));
        return null;
    }
    
    @Override
    public boolean matches(Object argument) {
        if(argument instanceof Event){
            Event evt = (Event) argument;
            return source==null || evt.getSource().equals(source);
        } else {
            return false;
        }
    }
    
    @Override
    public void appendTo(StringBuffer buffer) {
        buffer.append("event(").append(source.toString()).append(")");
    }

}
