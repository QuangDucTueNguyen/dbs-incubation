package contracts.hotel

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description("Create hotel should work with valid request")
    def rs = [name: "HOTEL_NAME", address: "ADDRESS", hotline: "123456789"]
    def rp = [name: "HOTEL_NAME", address: "ADDRESS", phoneNumber: "123456789"]
    request {
        method POST()
        url '/api/hotels'
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

