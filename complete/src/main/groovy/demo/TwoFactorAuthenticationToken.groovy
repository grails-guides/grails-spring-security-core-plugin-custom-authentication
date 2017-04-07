package demo

import groovy.transform.CompileStatic
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.CredentialsContainer
import org.springframework.security.core.GrantedAuthority

@CompileStatic
class TwoFactorAuthenticationToken extends UsernamePasswordAuthenticationToken {

    TwoFactorAuthenticationToken(Object principal, Object credentials) {
        super(principal, credentials)
    }
}
