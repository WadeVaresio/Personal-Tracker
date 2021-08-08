import React from "react";
import WeatherDetails from "../components/WeatherDetails";
import useSWR from "swr";
import {fetchWithoutToken} from "../services/fetch";
import {ImageBackground} from "react-native-web";
import {Card, Form} from "react-bootstrap";
import {View, Text} from "react-native";
import {useState} from "react";


const getFetchedWeatherDataLocation = (weatherData, userLocation) => {
    if(weatherData === undefined)
        return userLocation;

    return weatherData.location.name + ", " + weatherData.location.region + ", " + weatherData.location.country;
}


const Weather = () =>{
    const [location, setLocation] = useState("Goleta");
    const [newLocation, setNewLocation] = useState("");
    const { data: weatherData, error} = useSWR(`/api/weather/get?location=${encodeURIComponent(location)}`, fetchWithoutToken);
    const { data: backgroundImage } = useSWR(`/api/nasa/satImage/get?location=${encodeURIComponent(location)}`);
    console.log(backgroundImage);

    const handleNewLocationSubmit = (event) => {
        event.preventDefault();
        setLocation(newLocation);
        setNewLocation("");
    };

    const styles = {
        image: {
            flex: 1,
            resizeMode: "cover",
            justifyContent: "center",
            minWidth: "100%",
            minHeight: "100%"
        },
        titleText: {
            color: '#ffffff',
            fontSize: 25,
            verticalAlign: 'center',
            backgroundColor: 'rgba(0, 0, 0, 0.5)'
        },
        textInput: {
            backgroundColor: 'rgba(0, 0, 0, 0.5)'
        },
        text: {
          verticalAlign: 'center'
        },
        button: {
            height: 'auto'
        },
        formStyle: {
            backgroundColor: 'rgba(0, 0, 0, 0.5)'
        }
    };

    const errorCard = (
        <Card bg={"danger"}>
            <Card.Title>Error</Card.Title>
            <Card.Body>There has been an error fetching the weather</Card.Body>
        </Card>);

    return (
        <ImageBackground source={`/api/nasa/satImage/get?location=${encodeURIComponent(location)}`} style={styles.image}>
            <div className={"item-container"}>
                <View style={{flexDirection: 'column'}}>
                    <Text style={styles.titleText}>{getFetchedWeatherDataLocation(weatherData, location)}</Text>

                    <hr/>

                    {error && errorCard}
                    {weatherData && <WeatherDetails weatherData={weatherData}/>}

                    <hr/>

                    <View style={{flexDirection: 'row', justifyContent: 'center'}}>
                        <Form onSubmit={handleNewLocationSubmit}>
                            <Form.Control type={"text"}
                                          value={newLocation}
                                          placeholder={"New Location"}
                                          onChange={(change) => setNewLocation(change.target.value)}/>
                        </Form>
                        <Text>Powered by <a href="https://www.weatherapi.com/" title="Weather API">WeatherAPI.com</a></Text>
                    </View>
                </View>
            </div>
        </ImageBackground>
    );
};

export default Weather;