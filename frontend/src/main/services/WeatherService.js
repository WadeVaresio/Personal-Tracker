import fetch from "isomorphic-unfetch";

const fetchCurrentWeatherGoleta = async() => {
    const url = '/api/weather/current/goleta';
    const response = await fetch(url);

    return response;
}

const fetchForecastWeather = async(fields) => {
    const url = `/api/weather/forecast?location=${encodeURIComponent(fields.location)}`;

    return await fetch(url);
}

export { fetchCurrentWeatherGoleta, fetchForecastWeather };