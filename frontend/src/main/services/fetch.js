import unfetch from "isomorphic-unfetch";

export async function fetchWithoutToken(url, options) {
    const response = await unfetch(url, {
        ...options,
        headers: {
            ...options?.headers,
        },
    });
    if (response.status >= 400 && response.status < 600) {
        throw new Error(response.error_description);
    }
    if (options?.noJSON || response.status === 204) {
        return response;
    }
    return response.json();
}

export async function fetchWithToken(url, getToken, options) {
    const token = await getToken();
    const response = await unfetch(url, {
        ...options,
        headers: {
            ...options?.headers,
            Authorization: `Bearer ${token}`,
        },
    });
    if (response.status >= 400 && response.status < 600) {
        throw new Error(response.error_description);
    }
    if (options?.noJSON || response.status === 204) {
        return response;
    }
    return response.json();
}
