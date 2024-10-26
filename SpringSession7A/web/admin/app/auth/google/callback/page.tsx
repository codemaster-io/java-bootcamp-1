'use client'

import {useEffect} from 'react';

export default function GoogleCallback() {

    useEffect(() => {
        const queryString = window.location.search;
        const params = new URLSearchParams(queryString);
        const code = params.get('code');

        if (code) {
            console.log("Authorization code:", code);

            // Send the authorization code to the opener window
            window.opener.postMessage({ code }, window.location.origin);

            // Mark as processed to avoid re-processing

            // Optionally close the popup
            window.close();
        }
    }, []);

    // useEffect(() => {
    //     const hash = window.location.hash;
    //     if (hash) {
    //         const params = new URLSearchParams(hash.replace('#', ''));
    //         const idToken = params.get('id_token'); // Extract the access token
    //         console.log("Authorization code:", idToken);
    //
    //         if (idToken) {
    //             console.log("Authorization code:", idToken);
    //             // Send the authorization code to the opener window
    //             window.opener.postMessage({ idToken }, window.location.origin);
    //         }
    //         // Close the popup after sending the code
    //         // window.close();
    //     }
    //
    // }, []);



    return <p>Processing Sign-In...</p>;
}
