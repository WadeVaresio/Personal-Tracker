package com.varesio.wade.personaltracker.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import java.util.HashMap;

@RestClientTest(LocationService.class)
public class LocationServiceTests {

    @Autowired
    private MockRestServiceServer mockRestServiceServer;

    @Autowired
    private LocationService locationService;

    @Test
    public void test_getCoordinates(){
        String expectedURL = "https://nominatim.openstreetmap.org/search/?q=Goleta&format=json";

        String fakeJSON = "[{\"osm_type\":\"relation\",\"osm_id\":7080036,\"licence\":\"Data © OpenStreetMap contributors, ODbL 1.0. https://osm.org/copyright\",\"boundingbox\":[\"34.4137607\",\"34.45877\",\"-119.921877\",\"-119.8050597\"],\"importance\":0.5627124593811768,\"icon\":\"https://nominatim.openstreetmap.org/ui/mapicons//poi_boundary_administrative.p.20.png\",\"lon\":\"-119.8276389\",\"display_name\":\"Goleta, Santa Barbara County, California, United States\",\"type\":\"administrative\",\"class\":\"boundary\",\"place_id\":258955290,\"lat\":\"34.4358295\"}]";

        mockRestServiceServer.expect(requestTo(expectedURL))
                .andRespond(withSuccess(fakeJSON, MediaType.APPLICATION_JSON));

        HashMap<String, String> expected = new HashMap<>();
        expected.put("lon", "-119.8276389");
        expected.put("lat", "34.4358295");

        HashMap<String, String> actual = locationService.getCoordinates("Goleta");

        assertEquals(expected, actual);
    }
}
