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
            try {
                const response = await fetch(`/api/users?page=${currentPage}&limit=10`, {
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

            const response = await fetch('/api/users', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${token}`
                },
                body: JSON.stringify(newUser)
            })
            if (response.ok) {
                const addedUser = await response.json()
                setUsers(prevUsers => [...prevUsers, addedUser])
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

            const response = await fetch(`/api/users`, {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${token}`
                },
                body: JSON.stringify(editingUser)
            })
            if (response.ok) {
                const updatedUser = await response.json()
                setUsers(users.map(user => user.id === updatedUser.id ? updatedUser : user))
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
            try {
                const response = await fetch(`/api/users?id=${userId}`, {
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

            {/*<Modal isOpen={isEditingUser} onClose={() => setIsEditingUser(false)} title="Edit User">*/}
            {/*    {editingUser && (*/}
            {/*        <form onSubmit={handleEditUser} className="space-y-4">*/}
            {/*            <Input*/}
            {/*                label="Name"*/}
            {/*                value={editingUser.name}*/}
            {/*                onChange={(e) => setEditingUser({ ...editingUser, name: e.target.value })}*/}
            {/*                required*/}
            {/*            />*/}
            {/*            <Input*/}
            {/*                label="Email"*/}
            {/*                type="email"*/}
            {/*                value={editingUser.email}*/}
            {/*                onChange={(e) => setEditingUser({ ...editingUser, email: e.target.value })}*/}
            {/*                required*/}
            {/*            />*/}
            {/*            <div>*/}
            {/*                <label className="block text-sm font-medium text-gray-700">Role</label>*/}
            {/*                <select*/}
            {/*                    value={editingUser.role}*/}
            {/*                    onChange={(e) => setEditingUser({ ...editingUser, role: e.target.value })}*/}
            {/*                    className="mt-1 block w-full pl-3 pr-10 py-2 text-base border-gray-300 focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 sm:text-sm rounded-md"*/}
            {/*                >*/}
            {/*                    <option value="user">User</option>*/}
            {/*                    <option value="admin">Admin</option>*/}
            {/*                </select>*/}
            {/*            </div>*/}
            {/*            <div>*/}
            {/*                <label className="block text-sm font-medium text-gray-700">Permissions</label>*/}
            {/*                <div className="mt-2 space-y-2">*/}
            {/*                    <label className="inline-flex items-center">*/}
            {/*                        <input*/}
            {/*                            type="checkbox"*/}
            {/*                            className="form-checkbox"*/}
            {/*                            checked={editingUser.permissions.includes('add_user')}*/}
            {/*                            onChange={() => handleTogglePermission('add_user')}*/}
            {/*                        />*/}
            {/*                        <span className="ml-2">Permit to add User</span>*/}
            {/*                    </label>*/}
            {/*                    <label className="inline-flex items-center">*/}
            {/*                        <input*/}
            {/*                            type="checkbox"*/}
            {/*                            className="form-checkbox"*/}
            {/*                            checked={editingUser.permissions.includes('remove_user')}*/}
            {/*                            onChange={() => handleTogglePermission('remove_user')}*/}
            {/*                        />*/}
            {/*                        <span className="ml-2">Permit to remove User</span>*/}
            {/*                    </label>*/}
            {/*                    <label className="inline-flex items-center">*/}
            {/*                        <input*/}
            {/*                            type="checkbox"*/}
            {/*                            className="form-checkbox"*/}
            {/*                            checked={editingUser.permissions.includes('read_write')}*/}
            {/*                            onChange={() => handleTogglePermission('read_write')}*/}
            {/*                        />*/}
            {/*                        <span className="ml-2">Permit read/write operation</span>*/}
            {/*                    </label>*/}
            {/*                </div>*/}
            {/*            </div>*/}
            {/*            <Button type="submit">Update User</Button>*/}
            {/*        </form>*/}
            {/*    )}*/}
            {/*</Modal>*/}
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
            {/*<div className="flex justify-between">*/}
            {/*    <Button onClick={() => setCurrentPage(currentPage - 1)} disabled={currentPage === 1}>*/}
            {/*        Previous*/}
            {/*    </Button>*/}
            {/*    <Button onClick={() => setCurrentPage(currentPage + 1)} disabled={currentPage === totalPages}>*/}
            {/*        Next*/}
            {/*    </Button>*/}
            {/*</div>*/}
        </div>
    )
}