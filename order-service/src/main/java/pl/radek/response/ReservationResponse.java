package pl.radek.response;

import lombok.Data;

@Data
public class ReservationResponse implements Response {
    private String reservationUid;
    private String status;
}
