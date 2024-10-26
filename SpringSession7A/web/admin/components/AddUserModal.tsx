import React, { useState, useEffect } from 'react'
import { Modal } from './Modal'
import { Input } from './Input'
import { Button } from './Button'

interface User {
    name: string
    email: string
    password: string
    role: 'USER' | 'ADMIN' | 'MODERATOR'
    permissions: string[]
}

interface AddUserModalProps {
    isOpen: boolean
    onClose: () => void
    onAddUser: (user: User) => void
}

const rolePermissions = {
    USER: [],
    ADMIN: ['ADMIN:READ_PERMISSION', 'ADMIN:ALL_PERMISSION'],
    MODERATOR: ['MODERATOR:READ_PERMISSION', 'MODERATOR:ALL_PERMISSION'],
}

export function AddUserModal({ isOpen, onClose, onAddUser }: AddUserModalProps) {
    const [newUser, setNewUser] = useState<User>({
        name: '',
        email: '',
        password: '',
        role: 'ADMIN',
        permissions: [],
    })

    // useEffect(() => {
    //     setNewUser(prev => ({
    //         ...prev,
    //         permissions: rolePermissions[prev.role],
    //     }))
    // }, [newUser.role])

    const handleInputChange = (e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>) => {
        const { name, value } = e.target
        setNewUser(prev => ({ ...prev, [name]: value }))
    }

    const handleTogglePermission = (permission: string) => {
        setNewUser(prev => ({
            ...prev,
            permissions: prev.permissions.includes(permission)
                ? prev.permissions.filter(p => p !== permission)
                : [...prev.permissions, permission],
        }))
    }

    const handleSubmit = (e: React.FormEvent) => {
        e.preventDefault()
        onAddUser(newUser)
        onClose()
    }

    return (
        <Modal isOpen={isOpen} onClose={onClose} title="Add New User">
            <form onSubmit={handleSubmit} className="space-y-4">
                <Input
                    label="Name"
                    name="name"
                    value={newUser.name}
                    onChange={handleInputChange}
                    required
                />
                <Input
                    label="Email"
                    name="email"
                    type="email"
                    value={newUser.email}
                    onChange={handleInputChange}
                    required
                />
                <Input
                    label="Password"
                    name="password"
                    type="password"
                    value={newUser.password}
                    onChange={handleInputChange}
                    required
                />
                <div>
                    <label htmlFor="role" className="block text-sm font-medium text-gray-700">Role</label>
                    <select
                        id="role"
                        name="role"
                        value={newUser.role}
                        onChange={handleInputChange}
                        className="mt-1 block w-full pl-3 pr-10 py-2 text-base border-gray-300 focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 sm:text-sm rounded-md"
                    >
                        <option value="USER">User</option>
                        <option value="ADMIN">Admin</option>
                        <option value="MODERATOR">Moderator</option>
                    </select>
                </div>
                {newUser.role !== 'USER' && (
                    <div>
                        <label className="block text-sm font-medium text-gray-700">Permissions</label>
                        <div className="mt-2 space-y-2">
                            {rolePermissions[newUser.role].map(permission => (
                                <label key={permission} className="inline-flex items-center">
                                    <input
                                        type="checkbox"
                                        className="form-checkbox h-4 w-4 text-indigo-600 transition duration-150 ease-in-out"
                                        checked={newUser.permissions.includes(permission)}
                                        onChange={() => handleTogglePermission(permission)}
                                    />
                                    <span className="ml-2 text-sm text-gray-700">{permission}</span>
                                </label>
                            ))}
                        </div>
                    </div>
                )}
                <Button type="submit" className="w-full">Add User</Button>
            </form>
        </Modal>
    )
}