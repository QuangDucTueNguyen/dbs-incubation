package contracts.guest

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description("delete user should work")
    request {
        method DELETE()
        url('/api/guests/182b758f-f490-4187-a7e2-39a7332a13d6')
    }
    response {
        status NO_CONTENT()
        headers {
            contentType(applicationJson())
        }
    }
}
