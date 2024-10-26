'use client'

import { useState } from 'react'
import { useRouter } from 'next/navigation'
import { useAuth } from '@/contexts/AuthContext'
import { Button } from '@/components/Button'
import { Input } from '@/components/Input'
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import {faGithub, faGoogle} from '@fortawesome/free-brands-svg-icons';

export default function SignIn() {
    const [email, setEmail] = useState('')
    const [password, setPassword] = useState('')
    const router = useRouter()
    const { login, loginWithOAuth } = useAuth()

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault()
        const success = await login(email, password)
        if (success) {
            router.push('/dashboard')
        } else {
            alert('Invalid credentials')
        }
    }

    const generateNonce = () => {
        return Math.random().toString(36).substring(2, 15) + Math.random().toString(36).substring(2, 15);
    };

    /*const handleGoogleSignIn = () => {
        const googleAuthURL = "https://accounts.google.com/o/oauth2/v2/auth"
        const redirectURI = "http://localhost:4444/auth/google/callback" // Update as needed
        const clientID = "331269044409-ug38go205jj8udaiva5v30sikvhfad35.apps.googleusercontent.com"
        const responseType = "id_token"
        const scope = 'openid email profile';
        // const authURL = `https://accounts.google.com/o/oauth2/v2/auth?client_id=${clientID}&redirect_uri=${redirectURI}&response_type=code&scope=${scope}&access_type=${accessType}`;

        const nonce = generateNonce(); // Generate a nonce

        const authURL = `${googleAuthURL}?client_id=${clientID}&redirect_uri=${redirectURI}&response_type=${responseType}&scope=${scope}&nonce=${nonce}&prompt=login`;

        console.log("authUrl", authURL)

        // Open a new popup window for Google Sign-In
        const popup = window.open(authURL, "googleLogin", "width=500,height=600");

        // Listen for the redirect URL in the popup
        const messageListener = async (event: MessageEvent) => {
            if (event.origin !== window.location.origin) return; // Ensure it's from the same origin

            const { idToken } = event.data;  // Assuming the callback sends { code }

            if (idToken) {
                // // Close the popup after receiving the code
                popup?.close();

                console.log("Google Code: ", idToken)

                const success = await loginWithOAuth(idToken, 'Google')
                if (success) {
                    router.push('/dashboard')
                }
            }
        };

        // Attach the listener
        window.addEventListener("message", messageListener);

        // Clean up the listener when the component is unmounted or on next call
        return () => {
            window.removeEventListener("message", messageListener);
        };
    };
    */

    const handleGoogleSignIn = () => {
        const googleAuthURL = "https://accounts.google.com/o/oauth2/v2/auth"
        const redirectURI = "http://localhost:4444/auth/google/callback" // Update as needed
        const clientID = "331269044409-ug38go205jj8udaiva5v30sikvhfad35.apps.googleusercontent.com"
        const responseType = "code"
        const accessType = "offline"
        // const scope = 'openid email profile';
        const prompt = "consent"; // Always prompt for login

        const scope = [
            "email",
            "profile",
            // "https://www.googleapis.com/auth/calendar",
            // "https://www.googleapis.com/auth/drive",
            // "https://www.googleapis.com/auth/photoslibrary.readonly",
        ].join(" ");

        const nonce = generateNonce(); // Generate a nonce

        // const authURL = `${googleAuthURL}?client_id=${clientID}&redirect_uri=${redirectURI}&response_type=${responseType}&scope=${scope}&nonce=${nonce}&prompt=login`;
        const authURL = `${googleAuthURL}?client_id=${clientID}&redirect_uri=${redirectURI}&response_type=${responseType}&scope=${scope}&nonce=${nonce}&access_type=${accessType}&prompt=${prompt}`;

        console.log("authUrl", authURL)

        // Open a new popup window for Google Sign-In
        const googlePopup = window.open(authURL, "googleLogin", "width=500,height=600");

        // Listen for the redirect URL in the popup
        const googleMessageListener = async (event: MessageEvent) => {
            if (event.origin !== window.location.origin) return; // Ensure it's from the same origin

            const { code } = event.data;  // Assuming the callback sends { code }

            if (code) {
                // // Close the popup after receiving the code
                // popup?.close();

                console.log("Google Code: ", code)

                // const success = await loginWithOAuth(code, 'Google', 'code')
                // if (success) {
                //     window.removeEventListener("message", googleMessageListener);
                //     router.push('/dashboard')
                // }
            }
        };

        // Attach the listener
        window.addEventListener("message", googleMessageListener);
    };


    const handleGitHubSignIn = () => {
        const githubAuthURL = "https://github.com/login/oauth/authorize";
        const redirectURI = "http://localhost:4444/auth/github/callback"; // Update as needed
        const clientID = "Ov23liMZ5SUUR9Wb5tDQ"; // Replace with your GitHub Client ID
        // const scope = "user:email user repo"; // Requesting email, user, public and private repository access
        const scope = "user:email"; // Requesting email, user
        const prompt = "consent";

        // Generate a random state parameter for security (useful for CSRF protection)
        const state = generateNonce();

        const authURL = `${githubAuthURL}?client_id=${clientID}&redirect_uri=${redirectURI}&scope=${scope}&state=${state}&prompt=${prompt}`;

        console.log("authUrl", authURL);

        // Open a new popup window for GitHub Sign-In
        const popup = window.open(authURL, "githubLogin", "width=500,height=600");

        // Listen for the redirect URL in the popup
        const githubMessageListener = async (event) => {
            if (event.origin !== window.location.origin) return; // Ensure it's from the same origin

            const { code } = event.data; // Assuming the callback sends { code }

            if (code) {
                // Close the popup after receiving the code
                // popup?.close();

                console.log("Github Code: ", code)


                // const success = await loginWithOAuth(code, 'Github', 'code')
                // if (success) {
                //     window.removeEventListener("message", githubMessageListener);
                //     router.push('/dashboard')
                // }
            }
        };

        // Attach the listener
        window.addEventListener("message", githubMessageListener);
    };



    return (
        <div className="flex h-screen items-center justify-center bg-gray-50">
            <div className="w-full max-w-md">
                <form onSubmit={handleSubmit} className="bg-white shadow-md rounded px-8 pt-6 pb-8 mb-4">
                    <h1 className="text-2xl font-bold mb-6 text-center">Sign In</h1>
                    <Input
                        label="Email"
                        id="email"
                        type="email"
                        value={email}
                        onChange={(e) => setEmail(e.target.value)}
                        required
                    />
                    <Input
                        label="Password"
                        id="password"
                        type="password"
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                        required
                    />
                    <Button type="submit" className="w-full">
                        Sign In
                    </Button>

                    <Button type="button" className="w-full mt-4" onClick={handleGoogleSignIn} variant={"outline"}>
                        <FontAwesomeIcon icon={faGoogle} className="mr-2" />
                        Sign In with Google
                    </Button>

                    <Button type="button" className="w-full mt-4" onClick={handleGitHubSignIn} variant={"outline"}>
                        <FontAwesomeIcon icon={faGithub} className="mr-2" />
                        Sign In with Github
                    </Button>
                </form>
            </div>
        </div>
    )
}