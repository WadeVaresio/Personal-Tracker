import React from "react";
import {ImageBackground} from "react-native";
import Goleta_Open_Space from "../images/Goleta_Open_Space.jpg";
import {Container} from "react-bootstrap";
import SavedNotesTable from "../components/SavedNotesTable";

const SavedNotes = () => {
    const styles = {
        image: {
            flex: 1,
            resizeMode: "cover",
            justifyContent: "center",
            minWidth: "101%",
            minHeight: "100%"
        }
    };

    return (
        <>
            <ImageBackground source={Goleta_Open_Space} style={styles.image}>
                <Container style={{justifyContent: 'center'}}>
                    <hr/>
                    <SavedNotesTable/>
                </Container>
            </ImageBackground>
        </>
    );
}

export default SavedNotes;