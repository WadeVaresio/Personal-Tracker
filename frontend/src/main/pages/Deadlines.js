import React from "react";
import {ImageBackground} from "react-native";
import storke_tower from "../images/storke_tower.jpg";
import {Container} from "react-bootstrap";
import DeadlinesTable from "../components/DeadlinesTable";

const Deadlines = () => {
    const styles = {
        image: {
            flex: 1,
            resizeMode: "cover",
            justifyContent: "center",
            minWidth: "101%",
            minHeight: "100%"
        }
    };

    return(
        <>
            <ImageBackground source={storke_tower} style={styles.image}>
                <Container style={{justifyContent: 'center'}}>
                    <DeadlinesTable/>
                </Container>
            </ImageBackground>
        </>
    );
};

export default Deadlines;