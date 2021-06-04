package contracts.reservation

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description("Get all reservation in time should work")
    def rp = [
                [id: "132b758f-f490-4187-a7e2-39a7332a1235", roomTypeId: "1", hotelId: 2, numberRooms: 3,
               from: "2021-06-04T12:00:00.588759200Z", to: "2021-06-10T12:00:00.588759200Z"],
                [id: "282b758f-f490-4187-a7e2-39a7332a1789", roomTypeId: "2", hotelId: 3, numberRooms: 4,
               from: "2021-06-04T12:00:00.588759200Z", to: "2021-06-10T12:00:00.588759200Z"]
        ]
    request {
        method GET()
        url '/api/reservations?from=2021-06-04T12:00:00.588759200Z&to=2021-06-10T12:00:00.588759200Z'
    }
    response {
        status OK()
        body(rp)
        headers {
            contentType(applicationJson())
        }
    }
}
