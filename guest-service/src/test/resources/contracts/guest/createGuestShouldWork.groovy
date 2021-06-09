package contracts.guest

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description("Create user should work with valid request")
    def rs = [name: "NAME", address : "ADDRESS", phoneNumber: "123456789"]
    def rp = [name: "NAME", address : "ADDRESS", phoneNumber: "123456789"]
    request {
        method POST()
        url '/api/guests'
        body(rs)
    }
    response {
        status CREATED()
        body(rp)
        headers {
            contentType(applicationJson())
        }
    }
}
