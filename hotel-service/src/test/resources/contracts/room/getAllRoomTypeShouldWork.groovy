package contracts.room

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description("Create room should work with valid request")
    def rp = [[id: 1, type: "MASTER", numberPersonPerRoom: 2],
              [id: 2, type: "NORMAL", numberPersonPerRoom: 2]]
    request {
        method GET()
        url '/api/reservations'
    }
    response {
        status OK()
        body(rp)
        headers {
            contentType(applicationJson())
        }
    }
}
