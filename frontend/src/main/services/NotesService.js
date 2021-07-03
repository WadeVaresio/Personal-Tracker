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
}

export {saveNewNote, saveEditedNote}