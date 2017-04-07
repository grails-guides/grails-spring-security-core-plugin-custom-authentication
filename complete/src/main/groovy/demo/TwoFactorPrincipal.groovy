package demo

import groovy.transform.Canonical
import groovy.transform.CompileStatic

import java.security.Principal

@Canonical
@CompileStatic
class TwoFactorPrincipal implements Principal {
    String name
    String coordinatePosition
    String coordinateValue
}
