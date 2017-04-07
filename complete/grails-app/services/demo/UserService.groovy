package demo

import grails.compiler.GrailsCompileStatic
import grails.plugin.springsecurity.SpringSecurityService
import grails.transaction.Transactional

@Transactional
@GrailsCompileStatic
class UserService {

    SpringSecurityService springSecurityService

    @Transactional(readOnly = true)
    Set<Role> findAllAuthoritiesByUser(User user) {
        (UserRole.findAllByUser(user) as List<UserRole>)*.role as Set<Role>
    }

    User saveUserWithUsernameAndPassword(String username, String plainTextPassword, boolean flush = true) {
        def password = encodePassword(plainTextPassword)
        def user = new User(username: username, password: password)
        user.save(flush: flush)
    }

    User updateUserPassword(User user, String plainTextPassword, boolean flush = true) {
        def password = encodePassword(plainTextPassword)
        user.password = password
        user.save(flush: flush)
    }

    private String encodePassword(String plainTextPassword) {
        springSecurityService?.passwordEncoder ? springSecurityService.encodePassword(plainTextPassword) : plainTextPassword
    }
}