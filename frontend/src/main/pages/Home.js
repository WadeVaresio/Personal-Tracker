import React, {useEffect} from "react";
import background from "../images/background.png";
import {ImageBackground} from "react-native";
import {useState} from "react";
import {Card} from "react-bootstrap";


// https://stackoverflow.com/questions/8888491/how-do-you-display-javascript-datetime-in-12-hour-am-pm-format
function formatAMPM(date) {
    var hours = date.getHours();
    var minutes = date.getMinutes();
    var ampm = hours >= 12 ? 'pm' : 'am';
    hours = hours % 12;
    hours = hours ? hours : 12; // the hour '0' should be '12'
    minutes = minutes < 10 ? '0'+minutes : minutes;
    return hours + ':' + minutes + ' ' + ampm;
}


const Home = () => {
    const [value, setValue] = useState(new Date())

    useEffect(() => {
        const interval = setInterval(
            () => setValue(new Date()),
            1000
        );

        return () => {
            clearInterval(interval);
        }
    }, []);

    const styles = {
        image: {
            flex: 1,
            resizeMode: "cover",
            justifyContent: "center",
            minWidth: "101%",
            minHeight: "100%"
        },
        view: {
            flex: 1,
            justifyContent: 'center'
        },
        card: {
            width: '12rem',
            horizontal: 'center'
        }
    }

    return (
        <ImageBackground source={background} style={styles.image}>
            <div className={"home-card"}>
                <Card style={styles.card} bg={"primary"} text={"light"}>
                    <Card.Title>Welcome</Card.Title>
                    <Card.Subtitle>{formatAMPM(value) + " " +
                        value.toLocaleString('default', { month: 'long' }) +
                        " " +
                        value.getDate() + ", " +
                        value.getFullYear()}
                    </Card.Subtitle>
                </Card>
            </div>
        </ImageBackground>
    );
}

export default Home;