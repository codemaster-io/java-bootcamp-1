'use client'

import { useState, useEffect } from 'react'
import { useParams } from 'next/navigation'
import { useAuth } from '@/contexts/AuthContext'

export default function UserDetails() {
    const { id } = useParams()
    const [user, setUser] = useState<{
        name: string;
        email: string;
        role: string;
        imageUrl: string;
        permissions: string[];
    } | null>(null);
    const { token } = useAuth()

    useEffect(() => {
        const fetchUser = async () => {
            const baseUrl = process.env.NEXT_PUBLIC_API_BASE_URL || '';

            try {
                const response = await fetch(`${baseUrl}/api/users/${id}`, {
                    headers: { 'Authorization': `Bearer ${token}` }
                })
                const data = await response.json()
                setUser(data)
            } catch (error) {
                console.error('Error fetching user details:', error)
            }
        }

        if (token) {
            fetchUser()
        }
    }, [id, token])

    if (!user) return <div className="text-center">Loading...</div>

    return (
        <div className="bg-white shadow overflow-hidden sm:rounded-lg">
            <div className="px-4 py-5 sm:px-6">
                <h3 className="text-lg leading-6 font-medium text-gray-900">{user.name}</h3>
            </div>
            <div className="border-t border-gray-200">
                <dl>
                    <div className="bg-gray-50 px-4 py-5 sm:grid sm:grid-cols-3 sm:gap-4 sm:px-6">
                        <dt className="text-sm font-medium text-gray-500">Email</dt>
                        <dd className="mt-1 text-sm text-gray-900 sm:mt-0 sm:col-span-2">{user.email}</dd>
                    </div>
                    <div className="bg-white px-4 py-5 sm:grid sm:grid-cols-3 sm:gap-4 sm:px-6">
                        <dt className="text-sm font-medium text-gray-500">Role</dt>
                        <dd className="mt-1 text-sm text-gray-900 sm:mt-0 sm:col-span-2">{user.role}</dd>
                    </div>
                    <div className="bg-gray-50 px-4 py-5 sm:grid sm:grid-cols-3 sm:gap-4 sm:px-6">
                        <dt className="text-sm font-medium text-gray-500">Permissions</dt>
                        <dd className="mt-1 text-sm text-gray-900 sm:mt-0 sm:col-span-2">
                            {user.permissions && user.permissions.length > 0 ? (
                                <ul className="list-disc pl-5">
                                    {user.permissions.map((permission, index) => (
                                        <li key={index}>{permission}</li>
                                    ))}
                                </ul>
                            ) : (
                                "No permissions assigned"
                            )}
                        </dd>
                    </div>
                </dl>
            </div>

            {user.imageUrl && (
                <div className="px-4 py-5 sm:px-6">
                    <img src={user.imageUrl} alt={user.name} className="w-32 h-32 rounded-full" />
                </div>
            )}

        </div>
    )
}