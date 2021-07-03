const saveNewNote = async (linkData) => {
    const requestOptions = {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(linkData)
    };

    return await fetch('/api/savedNotes/new', requestOptions);
};

const saveEditedNote = async (editedNote) => {
    const requestOptions = {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(editedNote)
    };
    return await fetch(`/api/savedNotes/put`, requestOptions);
};

const deleteNote = async (note) => {
    const requestOptions = {
        method: 'DELETE',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(note)
    };

    return await fetch('/api/savedNotes/delete', requestOptions);
}

export {saveNewNote, saveEditedNote, deleteNote}