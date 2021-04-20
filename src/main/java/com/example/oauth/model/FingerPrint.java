package com.example.oauth.model;

import lombok.*;
import ua_parser.Client;

import javax.persistence.*;

import static java.util.Optional.ofNullable;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FingerPrint {

    @Id
    @GeneratedValue(strategy = IDENTITY)

    @Column(name = "finger_print_id")
    private Long id;
    private String os;
    @Column(name = "user_agent")
    private String userAgent;
    private String device;
    private String country;
    @Column(name = "country_code")
    private String countryCode;
    private String city;
    private String zip;
    private String lat;
    private String lon;
    @ManyToOne
    private User user;

    public static FingerPrint create(IpGeolocationResponse ipGeolocationResponse, Client client) {
        return FingerPrint.builder()
                .os(ofNullable(client.os).map(os -> os.family).orElse(null))
                .device(ofNullable(client.device).map(device -> device.family).orElse(null))
                .userAgent(ofNullable(client.userAgent).map(userAgent -> userAgent.family).orElse(null))
                .country(ipGeolocationResponse.getCountry())
                .city(ipGeolocationResponse.getCity())
                .countryCode(ipGeolocationResponse.getCountryCode())
                .lat(ipGeolocationResponse.getLat())
                .lon(ipGeolocationResponse.getLon())
                .zip(ipGeolocationResponse.getZip())
                .build();
    }
}
