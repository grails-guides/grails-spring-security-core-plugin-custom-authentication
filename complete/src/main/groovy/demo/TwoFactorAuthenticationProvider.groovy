package demo

import groovy.transform.CompileStatic
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.userdetails.UserDetails

@CompileStatic
class TwoFactorAuthenticationProvider extends DaoAuthenticationProvider {

    CoordinateValidator coordinateValidator

    protected void additionalAuthenticationChecks(UserDetails userDetails,
                                                  UsernamePasswordAuthenticationToken authentication)
            throws AuthenticationException {

        super.additionalAuthenticationChecks(userDetails, authentication)

        if (!(authentication instanceof TwoFactorAuthenticationToken)) {
            logger.debug("Authentication failed: authentication is not a TwoFactorAuthenticationToken");
            throw new BadCredentialsException(messages.getMessage(
                    "AbstractUserDetailsAuthenticationProvider.badCredentials",
                    "Bad credentials"));
        }

        def twoFactorAuthenticationToken = (TwoFactorAuthenticationToken) authentication

        if ( !(twoFactorAuthenticationToken.principal instanceof TwoFactorPrincipal) ) {
            logger.debug("Authentication failed: authenticationToken principal is not a TwoFactorPrincipal");
            throw new BadCredentialsException(messages.getMessage(
                    "AbstractUserDetailsAuthenticationProvider.badCredentials",
                    "Bad credentials"));
        }
        def principal = (TwoFactorPrincipal) twoFactorAuthenticationToken.principal


        if ( !coordinateValidator.isValidValueForPositionAndUserName(principal.coordinateValue, principal.coordinatePosition, principal.name) ) {
            logger.debug("Authentication failed: coordiante note valid");
            throw new BadCredentialsException(messages.getMessage(
                    "AbstractUserDetailsAuthenticationProvider.badCredentials",
                    "Bad credentials"));
        }
    }

}
