package contracts.hotel

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description("Get room by hotel id should work with valid request")
    def rp = [id: 1, name: "ROOM_NAME", hotelId: 1 ,hotelName: "HOTEL_NAME", address: "ADDRESS", phoneNumber: "123456789"]
    request {
        method GET()
        url '/api/hotels/1/rooms/1'
    }
    response {
        status CREATED()
        body(rp)
        headers {
            contentType(applicationJson())
        }
    }
}

