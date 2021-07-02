import React from "react";
import {Button, Modal, Form, Jumbotron} from "react-bootstrap";
import {useState} from "react";
import {saveNewLink} from "../services/SavedLinksService";
import BootstrapTable from "react-bootstrap-table-next";
import useSWR from "swr";
import {fetchWithoutToken} from "../services/fetch";
import {ImageBackground} from "react-native";
import Goleta_Open_Space from "../images/Goleta_Open_Space.jpg";


const SavedLinks = () => {
    const styles = {
        image: {
            flex: 1,
            resizeMode: "cover",
            justifyContent: "center",
            minWidth: "101%",
            minHeight: "100%"
        },
        table: {
            backgroundColor:'transparent'
        }
    };

    const [showModal, setShowModal] = useState(false);
    const [newLink, setNewLink] = useState("");
    const [newLinkNote, setNewLinkNote] = useState("");

    const handleShow = () => setShowModal(true);
    const handleClose = () => {
        setNewLink("");
        setNewLinkNote("");
        setShowModal(false);
    }

    const handleNewLink = (change) => {setNewLink(change.target.value)};
    const handleNewLinkNote = (change) => {setNewLinkNote(change.target.value)};

    const handleSubmit = () => {
        saveNewLink({link: newLink, note: newLinkNote});
        setShowModal(false)
    }

    const { data: allLinks, errorLinks, mutate: mutateLinks } = useSWR("/api/savedLinks/all", fetchWithoutToken);

    const tableColumns = [{
        dataField: 'link',
        text: 'Link'
    }, {
        dataField: 'note',
        text: 'Note'
    }];

    return (
        <>
            <ImageBackground source={Goleta_Open_Space} style={styles.image}>
                <Jumbotron style={styles.table}>
                    <Button variant="primary" onClick={handleShow}>
                        New Saved Link
                    </Button>
                    <BootstrapTable keyField={'id'} data={allLinks || []} columns={tableColumns}/>

                </Jumbotron>
            </ImageBackground>

            <Modal show={showModal} onHide={handleClose}>
                <Modal.Header closeButton>
                    <Modal.Title>New Link To Save</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <Form onSubmit={handleSubmit}>
                        <Form.Label>Link</Form.Label>
                        <Form.Control type={"url"} placeholder={"Link to save"} onChange={handleNewLink}/>
                        <Form.Label>Link Note</Form.Label>
                        <Form.Control type={"text"} placeholder={"Note regarding link to save"} onChange={handleNewLinkNote}/>
                    </Form>
                </Modal.Body>
                <Modal.Footer>
                    <Button variant="secondary" onClick={handleClose}>
                        Cancel
                    </Button>
                    <Button variant="primary" onClick={handleSubmit}>
                        Save Link
                    </Button>
                </Modal.Footer>
            </Modal>
        </>
    );
}

export default SavedLinks;