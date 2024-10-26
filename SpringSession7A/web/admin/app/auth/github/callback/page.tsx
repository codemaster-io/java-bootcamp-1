'use client'

import { useEffect, useState } from 'react';

export default function GithubCallback() {

    useEffect(() => {
        const queryString = window.location.search;
        const params = new URLSearchParams(queryString);
        const code = params.get('code');

        if (code) {
            console.log("Authorization code:", code);

            // Send the authorization code to the opener window
            window.opener.postMessage({ code }, window.location.origin);

            // Optionally close the popup
            window.close();
        }
    }, []);

    return <p>Processing Sign-In...</p>;
}
