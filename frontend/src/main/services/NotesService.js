const saveNewNote = async (note, tokenFetcher) => {
    const authToken = await tokenFetcher();

    const requestOptions = {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            Authorization: `Bearer ${authToken}`
        },
        body: JSON.stringify(note)
    };

    return await fetch('/api/private/savedNotes/new', requestOptions);
};

const saveEditedNote = async (editedNote, tokenFetcher) => {
    const authToken = await tokenFetcher();

    const requestOptions = {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
            Authorization: `Bearer ${authToken}`
        },
        body: JSON.stringify(editedNote)
    };
    return await fetch(`/api/private/savedNotes/put`, requestOptions);
};

const deleteNote = async (note, tokenFetcher) => {
    const authToken = await tokenFetcher();

    const requestOptions = {
        method: 'DELETE',
        headers: {
            'Content-Type': 'application/json',
            Authorization: `Bearer ${authToken}`
        },
        body: JSON.stringify(note)
    };

    return await fetch('/api/private/savedNotes/delete', requestOptions);
}


export {saveNewNote, saveEditedNote, deleteNote}
