package demo

import grails.compiler.GrailsCompileStatic
import grails.gorm.DetachedCriteria
import grails.transaction.Transactional

@Transactional
@GrailsCompileStatic
class UserRoleService {

    @Transactional(readOnly = true)
    UserRole findUserRoleByUserIdAndRoleId(long userId, long roleId) {
        criteriaFor(userId, roleId).get()
    }

    @Transactional(readOnly = true)
    boolean existsUserRoleByUserIdAndRoleId(long userId, long roleId) {
        criteriaFor(userId, roleId).count()
    }

    private DetachedCriteria criteriaFor(long userId, long roleId) {
        UserRole.where {
            user == User.load(userId) && role == Role.load(roleId)
        }
    }

    UserRole saveUserRoleWithUserAndRole(User user, Role role, boolean flush = false) {
        def userRole = new UserRole(user: user, role: role)
        userRole.save(flush: flush)
    }

    boolean deleteUserRoleByUserAndRole(User u, Role r) {
        if (u != null && r != null) {
            Number numberOfEntitiesDeleted = UserRole.where { user == u && role == r }.deleteAll()
            numberOfEntitiesDeleted as boolean
        }
        false
    }

    int deleteAllUserRoleByUser(User u) {
        u == null ? 0 : UserRole.where { user == u }.deleteAll() as int
    }

    int deleteAllUserRoleByRole(Role r) {
        r == null ? 0 : UserRole.where { role == r }.deleteAll() as int
    }
}