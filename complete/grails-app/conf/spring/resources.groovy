import demo.CoordinateValidatorService
import demo.TwoFactorAuthenticationFilter
import demo.TwoFactorAuthenticationProvider
import org.springframework.security.web.authentication.session.NullAuthenticatedSessionStrategy

// Place your Spring DSL code here
beans = {

    twoFactorAuthenticationFilter(TwoFactorAuthenticationFilter) {
        filterProcessesUrl = '/login/authenticate'
        authenticationSuccessHandler = ref('authenticationSuccessHandler')
        authenticationFailureHandler = ref('authenticationFailureHandler')
        authenticationManager = ref('authenticationManager')
        sessionAuthenticationStrategy = ref('sessionAuthenticationStrategy')
    }

    coordinateValidator(CoordinateValidatorService)

    twoFactorAuthenticationProvider(TwoFactorAuthenticationProvider) {
        coordinateValidator = ref('coordinateValidator')
        userDetailsService = ref('userDetailsService')
        passwordEncoder = ref('passwordEncoder')
        userCache = ref('userCache')
        saltSource = ref('saltSource')
        preAuthenticationChecks = ref('preAuthenticationChecks')
        postAuthenticationChecks = ref('postAuthenticationChecks')
        authoritiesMapper = ref('authoritiesMapper')
        hideUserNotFoundExceptions = true
    }

    sessionAuthenticationStrategy(NullAuthenticatedSessionStrategy)
}
