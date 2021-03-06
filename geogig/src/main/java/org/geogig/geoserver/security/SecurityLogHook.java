package org.geogig.geoserver.security;

import javax.annotation.Nullable;

import org.locationtech.geogig.api.AbstractGeoGigOp;
import org.locationtech.geogig.api.hooks.CannotRunGeogigOperationException;
import org.locationtech.geogig.api.hooks.CommandHook;

/**
 * Classpath {@link CommandHook command hook} that logs remotes related command events to by simply
 * delegating to {@link SecurityLogger}
 * 
 * @see SecurityLogger#interestedIn(Class)
 * @see SecurityLogger#logPre(AbstractGeoGigOp)
 * @see SecurityLogger#logPost(AbstractGeoGigOp, Object, RuntimeException)
 */
public class SecurityLogHook implements CommandHook {

    @Override
    public boolean appliesTo(Class<? extends AbstractGeoGigOp<?>> clazz) {
        return SecurityLogger.interestedIn(clazz);
    }

    @Override
    public <C extends AbstractGeoGigOp<?>> C pre(C command)
            throws CannotRunGeogigOperationException {
        SecurityLogger.logPre(command);
        return command;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T post(AbstractGeoGigOp<T> command, @Nullable Object retVal,
            @Nullable RuntimeException exception) throws Exception {
        SecurityLogger.logPost(command, retVal, exception);
        return (T) retVal;
    }

}
