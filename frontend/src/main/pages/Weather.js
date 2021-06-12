import React from "react";
import WeatherDetails from "../components/WeatherDetails";
import useSWR from "swr";
import {fetchWithoutToken} from "../services/fetch";

const Weather = () =>{
    const { data: weatherData} = useSWR("api/weather/goleta", fetchWithoutToken)

    const styling = {
        display: "flex",
        justifyContent: "center",
        alignItems: "center"
    };

    return(
        <div style={styling}>
            <WeatherDetails data={weatherData}/>
        </div>
    );
}

export default Weather;