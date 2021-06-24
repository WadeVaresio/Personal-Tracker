import React from "react";
import {Card, ListGroup} from "react-bootstrap";
import {View} from "react-native";

const createDateString = (date) => {
    try {
        date.setDate(date.getDate() + 1); // There is currently a bug where JS Date incorrectly parses string
        const month = date.toLocaleString('default', { month: 'long' });
        const day = date.getDate();
        return month + " " + day;
    }catch (e) {
        console.log("Error trying to create date string from" + e)
        return "Error";
    }
}

const createCurrentWeatherCard = (weatherData, cardStyle, cardListStyle) => {
    if(weatherData === undefined)
        return <></>

    const current = weatherData.current;

    return <Card style={cardStyle} border={"dark"}>
        <Card.Body>
            <Card.Title style={{color: cardListStyle.color}}>Current Weather</Card.Title>
            <Card.Subtitle className="mb-2 text-muted">Last Updated</Card.Subtitle>
            <Card.Text>
                <ListGroup variant={"flush"}>
                    <ListGroup.Item style={cardListStyle}>{current.condition.text}</ListGroup.Item>
                    <ListGroup.Item style={cardListStyle}>{"Feels Like: " + current.feelslike_f + "F"}</ListGroup.Item>
                    <ListGroup.Item style={cardListStyle}>{"Actual Temp: " + current.temp_f + "F"}</ListGroup.Item>
                    <ListGroup.Item style={cardListStyle}>{"Wind: " + current.wind_mph + " " + current.wind_dir}</ListGroup.Item>
                    <ListGroup.Item style={cardListStyle}>{"Wind Gusts: " + current.gust_mph + "MPH"}</ListGroup.Item>
                </ListGroup>
            </Card.Text>
        </Card.Body>
    </Card>
}

const createForecastWeatherCard = (weatherData, day, cardStyle, cardListStyle) => {
    if(weatherData === undefined)
        return <></>

    const forecastDay = weatherData.forecast.forecastday[day];
    const dateString = createDateString(new Date(forecastDay.date));

    const dateTitle = {
        0: "Today's Forecast",
        1: "Tomorrow's Forecast",
        2: "Two Days Out"
    };

    return <Card style={cardStyle} border={"dark"}>
        <Card.Body>
            <Card.Title style={{color: cardListStyle.color}}>{dateTitle[day]}</Card.Title>
            <Card.Subtitle className="mb-2 text-muted">{dateString}</Card.Subtitle>
            <Card.Text>
                <ListGroup variant={"light"}>
                    <ListGroup.Item style={cardListStyle}>{forecastDay.day.condition.text + " ~" + forecastDay.day.avgtemp_f + "F"}</ListGroup.Item>
                    <ListGroup.Item style={cardListStyle}>{"Max: " + forecastDay.day.maxtemp_f + "F Min: " + forecastDay.day.mintemp_f + "F"}</ListGroup.Item>
                    <ListGroup.Item style={cardListStyle}>{"Chance of Rain: " + forecastDay.day.daily_chance_of_rain + "%"}</ListGroup.Item>
                    <ListGroup.Item style={cardListStyle}>{"Max Wind: " + forecastDay.day.maxwind_mph + "MPH"}</ListGroup.Item>
                    <ListGroup.Item style={cardListStyle}>{"Average Humidity: " + forecastDay.day.avghumidity + "%"}</ListGroup.Item>
                </ListGroup>
            </Card.Text>
        </Card.Body>
    </Card>;
}

const WeatherDetails = ({weatherData}) => {
    const cardStyle = {marginLeft: '1em', backgroundColor: 'transparent'};
    const cardListStyle = {backgroundColor:'transparent', color: '#ffffff'};

    return (
        <div className={"item-container"}>
            <View style={{flexDirection: 'column', justifyContent: 'space-between'}}>
                <View style={{flexDirection: 'row'}}>
                    {createCurrentWeatherCard(weatherData, cardStyle, cardListStyle)}
                    {createForecastWeatherCard(weatherData, 0, cardStyle, cardListStyle)}
                    {createForecastWeatherCard(weatherData, 1, cardStyle, cardListStyle)}
                    {createForecastWeatherCard(weatherData, 2, cardStyle, cardListStyle)}
                </View>
            </View>
        </div>
    );
}

export default WeatherDetails;