'use client'

import { useEffect } from 'react'
import { useRouter } from 'next/navigation'
import { Button } from "@/components/Button"
import { useAuth } from '@/contexts/AuthContext'

export default function Home() {
    const router = useRouter()
    const { token } = useAuth()

    useEffect(() => {
        if (token) {
            router.push('/dashboard')
        }
    }, [token, router])

    return (
        <div className="flex flex-col items-center justify-center min-h-screen bg-gray-100">
            <h1 className="text-4xl font-bold mb-8">Welcome to Admin Dashboard</h1>
            <div className="space-x-4">
                <Button onClick={() => router.push('/signin')}>Sign In</Button>
                <Button onClick={() => router.push('/signup')} variant="outline">Sign Up</Button>
            </div>
        </div>
    )
}