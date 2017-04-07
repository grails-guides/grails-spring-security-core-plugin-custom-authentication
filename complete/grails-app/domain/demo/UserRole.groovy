package demo

import grails.compiler.GrailsCompileStatic
import groovy.transform.CompileStatic
import groovy.transform.ToString
import groovy.transform.TypeCheckingMode
import groovy.util.logging.Slf4j
import org.apache.commons.lang.builder.HashCodeBuilder

@Slf4j
@GrailsCompileStatic
@ToString(cache=true, includeNames=true, includePackage=false)
class UserRole implements Serializable {

	private static final long serialVersionUID = 1

	User user
	Role role

	UserRole(User u, Role r) {
		this()
		user = u
		role = r
	}

	@Override
	boolean equals(other) {
		if ( !(other instanceof UserRole) ) {
			return false
		}

		(other as UserRole).user?.id == user?.id && (other as UserRole).role?.id == role?.id
	}

	@Override
	int hashCode() {
		def builder = new HashCodeBuilder()
		if (user) {
			builder.append(user.id)
		}
		if (role) {
			builder.append(role.id)
		}
		builder.toHashCode()
	}

	static constraints = {
		role validator: { Role r, UserRole ur ->
			if (ur.user == null || ur.user.id == null) {
				return
			}
			boolean existing = existsUserRoleByUserIdAndRoleId(ur.user.id, r.id)
			if (existing) {
				return 'userRole.exists'
			}
		}
	}

	static mapping = {
		id composite: ['user', 'role']
		version false
	}

	@CompileStatic(TypeCheckingMode.SKIP)
	static boolean existsUserRoleByUserIdAndRoleId(Long userId, Long roleId) {
		UserRole.where { user == User.load(userId) &&  role == Role.load(roleId) }.count() as boolean
	}
}
