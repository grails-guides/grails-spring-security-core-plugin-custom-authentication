package demo

import grails.compiler.GrailsCompileStatic

@GrailsCompileStatic
class CoordinateValidatorService implements CoordinateValidator {

    @Override
    boolean isValidValueForPositionAndUserName(String value, String position, String username) {

        def criteria = SecurityCoordinate.createCriteria()
        def result = criteria.get {
            eq('position', position)
            eq('value', value)
            user {
                eq('username', username)
            }
        }
        result as boolean
    }
}