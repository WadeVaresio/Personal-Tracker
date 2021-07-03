import React from "react";
import BootstrapTable from "react-bootstrap-table-next";
import {Button, Modal, Form} from "react-bootstrap";
import 'bootstrap/dist/css/bootstrap.min.css';
import {useState} from "react";
import {saveEditedNote} from "../services/NotesService";


const SavedNotesTable = (notes) => {
    const [editNote, setEditNote] = useState();
    const [newNoteText, setNewNoteText] = useState("");
    const [showModal, setShowModal] = useState(false);

    const handleClose = () => setShowModal(false);

    const editNoteClick = (row) => {
        setEditNote(row);
        setNewNoteText(row.note);
        setShowModal(true);
    };

    const getEditButton = (cell, row, rowIndex) => {
        return (
            <>
                <Button onClick={() => editNoteClick(row)}>Edit</Button>
            </>
        );
    };

    const handleEditSubmit = () => {
        editNote.note = newNoteText;
        saveEditedNote(editNote);
        setShowModal(false);
    };

    const handleEditNote = (change) => { setNewNoteText(change.target.value); };

    const tableColumns = [ {
        hidden: true,
        dataField: 'id',
        text: 'ID'
    }, {
        dataField: 'note',
        text: 'Note'
    }, {
        dataField: 'edit',
        text: 'Edit',
        isDummyField: true,
        formatter: (cell, row, rowIndex) => getEditButton(cell, row, rowIndex)
    }];

    return(
        <>
            <BootstrapTable keyField={'id'} data={notes.notes || []} columns={tableColumns} className={"table table-responsive"}/>

            <Modal show={showModal} onHide={handleClose}>
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
                    <Button variant={"secondary"} onClick={handleClose}>Cancel</Button>
                    <Button variant={"primary"} onClick={handleEditSubmit}>Make Edit</Button>
                </Modal.Footer>
            </Modal>
        </>
    );
}

export default SavedNotesTable