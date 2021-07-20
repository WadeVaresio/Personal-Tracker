import React, {useState} from "react";
import BootstrapTable from "react-bootstrap-table-next";
import {Button, Form, Modal} from "react-bootstrap";
import 'bootstrap/dist/css/bootstrap.min.css';
import {deleteNote, saveEditedNote, saveNewNote} from "../services/SavedNotesService";
import {useAuth0} from "@auth0/auth0-react";
import paginationFactory from 'react-bootstrap-table2-paginator';
import useSWR from "swr";
import {fetchWithToken} from "../services/fetch";


const SavedNotesTable = () => {
    const [editNote, setEditNote] = useState();
    const [newNoteText, setNewNoteText] = useState("");
    const [showModalNewNote, setShowModalNewNote] = useState(false);
    const [showModalEditNote, setShowModalEditNote] = useState(false);
    const [newNote, setNewNote] = useState("");
    const {user, getAccessTokenSilently: getAuthToken} = useAuth0();
    const {data: notes, mutate: mutateNotes} = useSWR(['/api/private/savedNotes/get', getAuthToken], fetchWithToken);

    const handleCloseNewNote = () => setShowModalNewNote(false);
    const handleShowNewNote = () => setShowModalNewNote(true);
    const handleCloseEditNote = () => setShowModalEditNote(false);

    const handleNewNote = (change) => {
        setNewNote(change.target.value);
    };

    const handleEditNote = (change) => {
        setNewNoteText(change.target.value);
    };

    const handleNewNoteSubmit = (event) => {
        event.preventDefault();
        mutateNotes(saveNewNote({
            note: newNote,
            userID: user.email
        }, getAuthToken));
        setShowModalNewNote(false);
    };

    const editNoteClick = (row) => {
        setEditNote(row);
        setNewNoteText(row.note);
        setShowModalEditNote(true);
    };

    const getEditDeleteButtons = row => {
        return (
            <>
                <Button onClick={() => editNoteClick(row)} style={{marginRight: "15px"}}>Edit</Button>
                <Button variant={"danger"} onClick={() => mutateNotes(deleteNote(row, getAuthToken))}>Delete</Button>
            </>
        );
    };

    const handleEditSubmit = (event) => {
        event.preventDefault();
        editNote.note = newNoteText;
        saveEditedNote(editNote, getAuthToken);
        setShowModalEditNote(false);
    };

    const tableColumns = [{
        hidden: true,
        dataField: 'id',
        text: 'ID'
    }, {
        dataField: 'note',
        text: 'Note'
    }, {
        dataField: 'edit',
        text: 'Edit/Delete',
        isDummyField: true,
        formatter: (_cell, row) => getEditDeleteButtons(row)
    }];

    return (
        <>
            <div className={"container"}>
                <Button variant="primary" onClick={handleShowNewNote}>
                    New Note
                </Button>

                <hr/>

                <Modal show={showModalEditNote} onHide={handleCloseEditNote}>
                    <Modal.Header>
                        <Modal.Title>Edit Note</Modal.Title>
                    </Modal.Header>
                    <Modal.Body>
                        <Form onSubmit={handleEditSubmit}>
                            <Form.Label>Note</Form.Label>
                            <Form.Control type={"text"} defaultValue={newNoteText} onChange={handleEditNote}/>
                        </Form>
                    </Modal.Body>
                    <Modal.Footer>
                        <Button variant={"secondary"} onClick={handleCloseEditNote}>Cancel</Button>
                        <Button variant={"primary"} onClick={handleEditSubmit}>Make Edit</Button>
                    </Modal.Footer>
                </Modal>

                <Modal show={showModalNewNote} onHide={handleCloseNewNote}>
                    <Modal.Header closeButton>
                        <Modal.Title>New Note To Save</Modal.Title>
                    </Modal.Header>
                    <Modal.Body>
                        <Form onSubmit={handleNewNoteSubmit}>
                            <Form.Label>Note</Form.Label>
                            <Form.Control type={"text"} placeholder={"Note to save"} onChange={handleNewNote}/>
                        </Form>
                    </Modal.Body>
                    <Modal.Footer>
                        <Button variant="secondary" onClick={handleCloseNewNote}>
                            Cancel
                        </Button>
                        <Button variant="primary" onClick={handleNewNoteSubmit}>
                            Save Note
                        </Button>
                    </Modal.Footer>
                </Modal>

                <BootstrapTable keyField={'id'}
                                data={notes || []}
                                columns={tableColumns}
                                className={"table table-responsive"}
                                pagination={paginationFactory()}/>
            </div>
        </>
    );
};

export default SavedNotesTable;