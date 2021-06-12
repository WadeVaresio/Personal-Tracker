import fetch from "isomorphic-unfetch";

const fetchWeatherGoleta = async() => {
    const url = '/api/weather/goleta';
    const response = await fetch(url);

    return response;
}

export default fetchWeatherGoleta;