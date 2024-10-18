import React, { useState, useEffect } from 'react'
import { Modal } from './Modal'
import { Input } from './Input'
import { Button } from './Button'

interface User {
    id: string
    name: string
    email: string
    role: 'USER' | 'ADMIN' | 'MANAGER'
    permissions: string[]
}

interface EditUserModalProps {
    isOpen: boolean
    onClose: () => void
    onEditUser: (user: User) => void
    user: User | null
}

const rolePermissions = {
    USER: [],
    ADMIN: ['ADMIN:READ_PERMISSION', 'ADMIN:WRITE_PERMISSION'],
    MANAGER: ['MANAGER:READ_PERMISSION', 'MANAGER:WRITE_PERMISSION'],
}

export function EditUserModal({ isOpen, onClose, onEditUser, user }: EditUserModalProps) {
    const [editedUser, setEditedUser] = useState<User | null>(null)

    useEffect(() => {
        if (user) {
            setEditedUser({ ...user })
        }
    }, [user])

    // useEffect(() => {
    //     if (editedUser) {
    //         setEditedUser(prev => ({
    //             ...prev,
    //             permissions: rolePermissions[prev?.role],
    //         }))
    //     }
    // }, [editedUser?.role])

    const handleInputChange = (e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>) => {
        const { name, value } = e.target
        setEditedUser(prev => prev ? { ...prev, [name]: value } : null)
    }

    const handleTogglePermission = (permission: string) => {
        setEditedUser(prev => {
            if (!prev) return null
            return {
                ...prev,
                permissions: prev.permissions.includes(permission)
                    ? prev.permissions.filter(p => p !== permission)
                    : [...prev.permissions, permission],
            }
        })
    }

    const handleSubmit = (e: React.FormEvent) => {
        e.preventDefault()
        if (editedUser) {
            onEditUser(editedUser)
            onClose()
        }
    }

    if (!editedUser) return null

    return (
        <Modal isOpen={isOpen} onClose={onClose} title="Edit User">
            <form onSubmit={handleSubmit} className="space-y-4">
                <Input
                    label="Name"
                    name="name"
                    value={editedUser.name}
                    onChange={handleInputChange}
                    required
                />
                <Input
                    label="Email"
                    name="email"
                    type="email"
                    value={editedUser.email}
                    onChange={handleInputChange}
                    required
                />
                <div>
                    <label htmlFor="role" className="block text-sm font-medium text-gray-700">Role</label>
                    <select
                        id="role"
                        name="role"
                        value={editedUser.role}
                        onChange={handleInputChange}
                        className="mt-1 block w-full pl-3 pr-10 py-2 text-base border-gray-300 focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 sm:text-sm rounded-md"
                    >
                        <option value="USER">User</option>
                        <option value="ADMIN">Admin</option>
                        <option value="MANAGER">Manager</option>
                    </select>
                </div>
                {editedUser.role !== 'USER' && (
                    <div>
                        <label className="block text-sm font-medium text-gray-700">Permissions</label>
                        <div className="mt-2 space-y-2">
                            {rolePermissions[editedUser.role].map(permission => (
                                <label key={permission} className="inline-flex items-center">
                                    <input
                                        type="checkbox"
                                        className="form-checkbox h-4 w-4 text-indigo-600 transition duration-150 ease-in-out"
                                        checked={editedUser?.permissions.includes(permission)}
                                        onChange={() => handleTogglePermission(permission)}
                                    />
                                    <span className="ml-2 text-sm text-gray-700">{permission}</span>
                                </label>
                            ))}
                        </div>
                    </div>
                )}
                <Button type="submit" className="w-full">Update User</Button>
            </form>
        </Modal>
    )
}