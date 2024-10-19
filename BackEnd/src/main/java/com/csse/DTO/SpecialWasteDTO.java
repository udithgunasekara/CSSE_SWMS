package com.csse.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SpecialWasteDTO {

    private String id; //unique identifier
    private String UUID; // auth identifier
    private String name;
    private Long number;
    private String city;
    private String address;
    private String typeofWaste;
    private String status;
    private String date;
}
