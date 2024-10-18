'use client'

import React, { createContext, useContext, useState, useEffect } from 'react'
import { useRouter } from 'next/navigation'

interface AuthContextType {
    token: string | null
    role: string | null
    userName: string | null,
    isLoading: boolean
    login: (email: string, password: string) => Promise<boolean>
    signup: (userData: { name: string; email: string; password: string }) => Promise<boolean>
    logout: () => void
}

const AuthContext = createContext<AuthContextType | null>(null)

export const AuthProvider: React.FC<{ children: React.ReactNode }> = ({ children }) => {
    const [token, setToken] = useState<string | null>(null)
    const [role, setRole] = useState<string | null>(null)
    const [userName, setUserName] = useState<string | null>('User')
    const [isLoading, setIsLoading] = useState(true)
    const router = useRouter()

    useEffect(() => {
        const initializeAuth = async () => {
            try {
                const storedToken = localStorage.getItem('token')
                if (storedToken) {
                    setToken(storedToken)
                    const decodedToken = JSON.parse(atob(storedToken.split('.')[1]))
                    console.log("decoded token: ", decodedToken)
                    setRole(decodedToken.role)
                    setUserName(decodedToken.userName)
                }
            } catch (error) {
                console.error('Error initializing auth:', error)
                // Clear potentially corrupted token
                localStorage.removeItem('token')
            } finally {
                setIsLoading(false)
            }
        }

        initializeAuth()
    }, [])

    const login = async (email: string, password: string): Promise<boolean> => {
        try {
            const response = await fetch('/api/auth/signin', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ email, password }),
            })

            if (!response.ok) {
                throw new Error('Login failed')
            }

            const data = await response.json()
            if (data.token) {
                setToken(data.token)
                localStorage.setItem('token', data.token)
                const decodedToken = JSON.parse(atob(data.token.split('.')[1]))
                setRole(decodedToken.role)
                return true
            }
            return false
        } catch (error) {
            console.error('Login error:', error)
            return false
        }
    }

    const signup = async (userData: { name: string; email: string; password: string }): Promise<boolean> => {
        try {
            const response = await fetch('/api/auth/signup', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(userData),
            })

            if (!response.ok) {
                throw new Error('Signup failed')
            }

            const data = await response.json()
            if (data.token) {
                setToken(data.token)
                localStorage.setItem('token', data.token)
                const decodedToken = JSON.parse(atob(data.token.split('.')[1]))
                setRole(decodedToken.role)
                return true
            }
            return false
        } catch (error) {
            console.error('Signup error:', error)
            return false
        }
    }

    const logout = () => {
        setToken(null)
        setRole(null)
        setUserName(null)
        localStorage.removeItem('token')
        router.push('/signin')
    }

    if (isLoading) {
        return (
            <div className="flex items-center justify-center min-h-screen">
                <div className="animate-spin rounded-full h-32 w-32 border-t-2 border-b-2 border-blue-500"></div>
            </div>
        )
    }

    return (
        <AuthContext.Provider value={{ token, role, userName, isLoading, login, signup, logout }}>
            {children}
        </AuthContext.Provider>
    )
}

export const useAuth = () => {
    const context = useContext(AuthContext)
    if (!context) {
        throw new Error('useAuth must be used within an AuthProvider')
    }
    return context
}