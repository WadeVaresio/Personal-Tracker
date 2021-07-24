import React from "react";
import WeatherDetails from "../components/WeatherDetails";
import useSWR from "swr";
import {fetchWithoutToken} from "../services/fetch";
import {ImageBackground} from "react-native-web";
import {Button, Card, Form, Row} from "react-bootstrap";
import {View, Text, TextInput} from "react-native";
import {useState} from "react";


const Weather = () =>{
    const [location, setLocation] = useState("Goleta");
    const [newLocation, setNewLocation] = useState("");
    const { data: weatherData, error} = useSWR(`/api/weather?location=${encodeURIComponent(location)}`, fetchWithoutToken)

    const handleNewLocationSubmit = (event) => {
        event.preventDefault();
        setLocation(newLocation);
        setNewLocation("");
    }

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
            verticalAlign: 'center'
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
        <ImageBackground source={"/api/nasa/satImage/Goleta"} style={styles.image}>
            <div className={"item-container"}>
                <View style={{flexDirection: 'column'}}>
                    <Text style={styles.titleText}>{location + " Weather"}</Text>

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
                    </View>
                </View>
            </div>
        </ImageBackground>
    );
};

export default Weather;