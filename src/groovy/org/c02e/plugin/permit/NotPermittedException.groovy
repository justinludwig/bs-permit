package org.c02e.plugin.permit

class NotPermittedException extends Exception {
    
    NotPermittedException() {
        super()
    }
    
    NotPermittedException(String message) {
        super(message)
    }
    
    NotPermittedException(String message, Throwable cause) {
        super(message, cause)
    }
    
    NotPermittedException(Throwable cause) {
        super(cause)
    }
}
