import React from "react";
import background from "../images/background.png";
import {ImageBackground} from "react-native";


const Home = () => {
    const styles = {
        image: {
            flex: 1,
            resizeMode: "cover",
            justifyContent: "center",
            minWidth: "101.5%",
            minHeight: "100%"
        }
    }

    return (
        <ImageBackground source={background} style={styles.image}>

        </ImageBackground>
    );
}

export default Home;