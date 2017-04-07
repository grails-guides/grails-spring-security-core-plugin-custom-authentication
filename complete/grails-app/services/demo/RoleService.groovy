package demo

import grails.compiler.GrailsCompileStatic
import grails.transaction.Transactional

@GrailsCompileStatic
@Transactional
class RoleService {

    Role saveRoleWithAuthority(String authority, boolean flush = false) {
        def role = Role.findByAuthority(authority)
        if ( role ) {
            log.debug("role with ${authority} already existed, no need to create a new one")
            return role
        }
        role = new Role(authority: authority)
        role.save(flush: flush)
    }

}