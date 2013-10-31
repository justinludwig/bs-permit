import com.pitchstone.plugin.permit.LogSuppressingExceptionResolver

beans = {
    springConfig.addAlias 'basicSessionlessPermitContextService', 'testPermitContextService'

    exceptionHandler(LogSuppressingExceptionResolver) {
        exceptionMappings = ['java.lang.Exception': 'error']
        grailsApplication = ref('grailsApplication')
    }
}
