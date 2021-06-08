package contracts.guest

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description("get user should work")
    def rp = [id: "182b758f-f490-4187-a7e2-39a7332a13d6", name: "Test_Contract"]
    request {
        method GET()
        url '/api/guests/182b758f-f490-4187-a7e2-39a7332a13d6'
    }
    response {
        status OK()
        body(rp)
        headers {
            contentType(applicationJson())
        }
    }
}
