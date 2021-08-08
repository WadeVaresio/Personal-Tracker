import fetch from "isomorphic-unfetch";

const fetchForecastWeather = async(fields) => {
    const url = `/api/weather/get?location=${encodeURIComponent(fields.location)}`;

    return await fetch(url);
}

export { fetchForecastWeather };