import React from "react";
import {Card} from "react-bootstrap";


const WeatherDetails = ({data}) => {
    return (
        <div style={{}}>
            <h1>Test</h1>

            <div className={"item-container"}>
                <Card>
                    <Card.Body>
                        <Card.Title>Actual Temperature</Card.Title>
                        <Card.Text>{"Actual: " + (data ? data.current.temp_f : "NULL") + "F"}</Card.Text>
                    </Card.Body>
                </Card>
                <Card>
                    <Card.Body>
                        <Card.Title>Real Feel Temperature</Card.Title>
                        <Card.Text>{"Feels Like: " + (data ? data.current.feelslike_f : "NULL") + "F"}</Card.Text>
                    </Card.Body>
                </Card>
                <Card>
                    <Card.Body>
                        <Card.Title>Wind</Card.Title>
                        <Card.Text>
                            {(data ? data.current.gust_mph : "NULL") + "MPH " + (data ? data.current.wind_dir : "NULL")}
                        </Card.Text>
                    </Card.Body>
                </Card>
            </div>
        </div>
    );
}

export default WeatherDetails;