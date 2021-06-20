import React from "react";
import WeatherDetails from "../components/WeatherDetails";
import useSWR from "swr";
import {fetchWithoutToken} from "../services/fetch";
import {ImageBackground} from "react-native";

const Weather = () =>{
    const { data: weatherData} = useSWR("api/weather/goleta", fetchWithoutToken)

    const styles = {
        image: {
            flex: 1,
            resizeMode: "cover",
            justifyContent: "center",
            minWidth: "101.5%",
            minHeight: "100%"
        }
    }

    return(
        <ImageBackground source={"/api/nasa/satImage/Goleta"} style={styles.image}>
            <WeatherDetails data={weatherData}/>
        </ImageBackground>
    );
}

export default Weather;