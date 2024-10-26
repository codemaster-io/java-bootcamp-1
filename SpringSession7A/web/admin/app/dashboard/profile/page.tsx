'use client'

import { useState, useEffect } from 'react'
import { useAuth } from '@/contexts/AuthContext'
import { Button } from '@/components/Button'
import { Input } from '@/components/Input'

export default function Profile() {
    const [user, setUser] = useState({ name: '', email: '', role: '', permissions: [] })
    const [isEditing, setIsEditing] = useState(false)
    const { token } = useAuth()

    useEffect(() => {
        const fetchProfile = async () => {
            const baseUrl = process.env.NEXT_PUBLIC_API_BASE_URL || '';

            try {
                const response = await fetch(`${baseUrl}/api/auth/user`, {
                    headers: { 'Authorization': `Bearer ${token}` }
                })
                const data = await response.json()
                setUser(data)
            } catch (error) {
                console.error('Error fetching profile:', error)
            }
        }

        if (token) {
            fetchProfile()
        }
    }, [token])

    const handleInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const { name, value } = e.target
        setUser(prevUser => ({ ...prevUser, [name]: value }))
    }

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault()
        try {
            const response = await fetch('/api/profile', {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${token}`
                },
                body: JSON.stringify(user)
            })
            if (response.ok) {
                setIsEditing(false)
            } else {
                console.error('Failed to update profile')
            }
        } catch (error) {
            console.error('Error updating profile:', error)
        }
    }

    return (
        <div className="max-w-2xl mx-auto mt-8">
            <h2 className="text-2xl font-bold mb-4">Profile</h2>
            <div className="bg-white shadow-md rounded-lg p-6">
                {isEditing ? (
                    <form onSubmit={handleSubmit} className="space-y-4">
                        <Input
                            label="Name"
                            id="name"
                            name="name"
                            value={user.name}
                            onChange={handleInputChange}
                            required
                        />
                        <Input
                            label="Email"
                            id="email"
                            name="email"
                            type="email"
                            value={user.email}
                            onChange={handleInputChange}
                            required
                        />
                        <div className="flex justify-end space-x-2">
                            <Button type="button" variant="secondary" onClick={() => setIsEditing(false)}>
                                Cancel
                            </Button>
                            <Button type="submit">Save Changes</Button>
                        </div>
                    </form>
                ) : (
                    <div className="space-y-4">
                        <div>
                            <label className="block text-sm font-medium text-gray-700">Name</label>
                            <p className="mt-1 text-sm text-gray-900">{user.name}</p>
                        </div>
                        <div>
                            <label className="block text-sm font-medium text-gray-700">Email</label>
                            <p className="mt-1 text-sm text-gray-900">{user.email}</p>
                        </div>
                        <div>
                            <label className="block text-sm font-medium text-gray-700">Role</label>
                            <p className="mt-1 text-sm text-gray-900">{user.role}</p>
                        </div>

                        <div>
                            <label className="block text-sm font-medium text-gray-700">Permissions</label>
                            <p className="mt-1 text-sm text-gray-900">{user.permissions}</p>
                        </div>
                        <div className="flex justify-end">
                            <Button onClick={() => setIsEditing(true)}>Edit Profile</Button>
                        </div>
                    </div>
                )}
            </div>
        </div>
    )
}