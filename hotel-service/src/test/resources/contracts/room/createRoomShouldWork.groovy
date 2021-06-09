package contracts.room

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description("Create room should work with valid request")
    def rs = [name: "ROOM_NAME_1", description: "DESCRIPTION"]
    def rp = [name: "ROOM_NAME_1", description: "DESCRIPTION"]
    request {
        method POST()
        url '/api/rooms'
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