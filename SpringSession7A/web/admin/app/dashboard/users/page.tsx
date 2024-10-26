'use client'

import { useState, useEffect } from 'react'
import { useRouter } from 'next/navigation'
import Link from 'next/link'
import { useAuth } from '@/contexts/AuthContext'
import { Button } from '@/components/Button'
import { Input } from '@/components/Input'
import { Modal } from '@/components/Modal'
import {AddUserModal} from "@/components/AddUserModal";
import {EditUserModal} from "@/components/EditUserModal";

interface User {
    id: number
    name: string
    email: string
    role: string
    imageUrl: string
    permissions: string[]
}

export default function Users() {
    const [users, setUsers] = useState<User[]>([])
    const [currentPage, setCurrentPage] = useState(1)
    const [totalPages, setTotalPages] = useState(1)
    const [isAddingUser, setIsAddingUser] = useState(false)
    const [isEditingUser, setIsEditingUser] = useState(false)
    const [editingUser, setEditingUser] = useState<User | null>(null)
    const [newUser, setNewUser] = useState({ name: '', email: '', password: '', role: 'USER', permissions: [] })
    const { token, role } = useAuth()
    const router = useRouter()

    useEffect(() => {
        if (role !== 'ADMIN') {
            router.push('/dashboard')
        }
    }, [role, router])

    useEffect(() => {
        const fetchUsers = async () => {
            const baseUrl = process.env.NEXT_PUBLIC_API_BASE_URL || '';

            try {
                const response = await fetch(`${baseUrl}/api/users`, {
                    method: 'GET',
                    headers: { 'Authorization': `Bearer ${token}` }
                })
                const data = await response.json()
                setUsers(data.users)
                setTotalPages(Math.ceil(data.total / 10))
            } catch (error) {
                console.error('Error fetching users:', error)
            }
        }

        if (token && role === 'ADMIN') {
            fetchUsers()
        }
    }, [currentPage, token, role])

    const handleAddUser = async (newUser: Omit<User, 'id'>) => {try {
            console.log("AddUser: ", newUser)
        const baseUrl = process.env.NEXT_PUBLIC_API_BASE_URL || '';

        const response = await fetch(`${baseUrl}/api/users`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${token}`
                },
                body: JSON.stringify(newUser)
            })
            if (response.ok) {
                const data = await response.json()
                setUsers(prevUsers => [...prevUsers, data.user])
                setIsAddingUser(false)
                setNewUser({ name: '', email: '', password: '', role: 'USER', permissions: [] })
            } else {
                console.error('Failed to add user')
            }
        } catch (error) {
            console.error('Error adding user:', error)
        }
    }

    const handleEditUser = async (editedUser: Omit<User, 'id'>) => {try {
            if (!editingUser) return
            const baseUrl = process.env.NEXT_PUBLIC_API_BASE_URL || '';

            const response = await fetch(`${baseUrl}/api/users`, {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${token}`
                },
                body: JSON.stringify(editingUser)
            })
            if (response.ok) {
                const data = await response.json()
                setUsers(users.map(user => user.id === data.user.id ? data.user : user))
                setIsEditingUser(false)
                setEditingUser(null)
            } else {
                console.error('Failed to update user')
            }
        } catch (error) {
            console.error('Error updating user:', error)
        }
    }

    const handleDeleteUser = async (userId: number) => {
        if (window.confirm('Are you sure you want to delete this user?')) {
            const baseUrl = process.env.NEXT_PUBLIC_API_BASE_URL || '';

            try {
                const response = await fetch(`${baseUrl}/api/users/${userId}`, {
                    method: 'DELETE',
                    headers: { 'Authorization': `Bearer ${token}` }
                })
                if (response.ok) {
                    setUsers(users.filter(user => user.id !== userId))
                } else {
                    console.error('Failed to delete user')
                }
            } catch (error) {
                console.error('Error deleting user:', error)
            }
        }
    }

    const handleTogglePermission = (permission: string) => {
        if (isEditingUser && editingUser) {
            setEditingUser(prev => ({
                ...prev,
                permissions: prev.permissions.includes(permission)
                    ? prev.permissions.filter(p => p !== permission)
                    : [...prev.permissions, permission]
            }))
        } else {
            setNewUser(prev => ({
                ...prev,
                permissions: prev.permissions.includes(permission)
                    ? prev.permissions.filter(p => p !== permission)
                    : [...prev.permissions, permission]
            }))
        }
    }

    if (role !== 'ADMIN') {
        return null
    }

    return (
        <div className="space-y-6">
            <h2 className="text-2xl font-bold">Users</h2>
            <Button onClick={() => setIsAddingUser(true)}>Add New User</Button>
            <AddUserModal
                isOpen={isAddingUser}
                onClose={() => setIsAddingUser(false)}
                onAddUser={handleAddUser}
            />

            <EditUserModal
                isOpen={isEditingUser}
                onClose={() => setIsEditingUser(false)}
                onEditUser={handleEditUser}
                user={editingUser}
            />

            <div className="bg-white shadow overflow-hidden sm:rounded-lg">
                <table className="min-w-full divide-y divide-gray-200">
                    <thead className="bg-gray-50">
                    <tr>
                        <th scope="col" className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">ID</th>
                        <th scope="col" className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Name</th>
                        <th scope="col" className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Email</th>
                        <th scope="col" className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Role</th>
                        <th scope="col" className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Actions</th>
                    </tr>
                    </thead>
                    <tbody className="bg-white divide-y divide-gray-200">
                    {users.map((user) => (
                        <tr key={user.id}>
                            <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-500">{user.id}</td>
                            <td className="px-6 py-4 whitespace-nowrap text-sm font-medium text-gray-900">
                                <Link href={`/dashboard/users/${user.id}`} className="text-blue-600 hover:text-blue-900">
                                    {user.name}
                                </Link>
                            </td>
                            <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-500">
                                <Link href={`/dashboard/users/${user.id}`} className="text-blue-600 hover:text-blue-900">
                                    {user.email}
                                </Link>
                            </td>
                            <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-500">{user.role}</td>
                            <td className="px-6 py-4 whitespace-nowrap text-sm font-medium">
                                <Button variant="secondary" size="sm" onClick={() => {
                                    setEditingUser(user)
                                    setIsEditingUser(true)
                                }}>Edit</Button>
                                <Button variant="secondary" size="sm" className="ml-2" onClick={() => handleDeleteUser(user.id)}>Delete</Button>
                            </td>
                        </tr>
                    ))}
                    </tbody>
                </table>
            </div>
        </div>
    )
}