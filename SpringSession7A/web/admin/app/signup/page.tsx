'use client'

import { useState } from 'react'
import { useRouter } from 'next/navigation'
import { useAuth } from '@/contexts/AuthContext'
import { Button } from '@/components/Button'
import { Input } from '@/components/Input'

export default function SignUp() {
    const [name, setName] = useState('')
    const [email, setEmail] = useState('')
    const [password, setPassword] = useState('')
    const router = useRouter()
    const { signup } = useAuth()

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault()
        const success = await signup({ name, email, password })
        if (success) {
            router.push('/dashboard')
        } else {
            alert('Signup failed')
        }
    }

    return (
        <div className="flex h-screen items-center justify-center bg-gray-50">
            <div className="w-full max-w-md">
                <form onSubmit={handleSubmit} className="bg-white shadow-md rounded px-8 pt-6 pb-8 mb-4">
                    <h1 className="text-2xl font-bold mb-6 text-center">Sign Up</h1>
                    <Input
                        label="Name"
                        id="name"
                        type="text"
                        value={name}
                        onChange={(e) => setName(e.target.value)}
                        required
                    />

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
                        Sign Up
                    </Button>
                </form>
            </div>
        </div>
    )
}