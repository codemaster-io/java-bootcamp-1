'use client'

import { useEffect, useState } from 'react'
import { useAuth } from '@/contexts/AuthContext'
import {useRouter} from "next/navigation";

interface Product {
    id: number
    name: string
    price: number
}

interface User {
    id: number
    name: string
    email: string
    role: string
    imageUrl: string
}

export default function Dashboard() {
    const [lastProducts, setLastProducts] = useState<Product[]>([])
    const [lastUsers, setLastUsers] = useState<User[]>([])
    const { token, role, userName} = useAuth()
    const router = useRouter()


    useEffect(() => {
        console.log("Username: ", userName)
        console.log("token", token)
        console.log("role", role)

        const fetchData = async () => {
            const baseUrl = process.env.NEXT_PUBLIC_API_BASE_URL || '';

            try {
                const productsResponse = await fetch(`${baseUrl}/api/products`, {
                    method: 'GET',
                    headers: {'Authorization': `Bearer ${token}`}
                })
                if (productsResponse.ok) {
                    const productsData = await productsResponse.json()
                    setLastProducts(productsData.products)
                    console.log(lastProducts);
                }

                const usersResponse = await fetch(`${baseUrl}/api/users`, {
                    method: 'GET',
                    headers: {'Authorization': `Bearer ${token}`}
                })
                if (usersResponse.ok) {
                    const usersData = await usersResponse.json()
                    setLastUsers(usersData.users)
                }
            } catch (error) {
                console.error('Error fetching dashboard data:', error)
            }
        }

        if (token) {
            fetchData()
        }
    }, [token])

    return (
        <div className="space-y-8">
            <h2 className="text-3xl font-bold text-gray-800">Dashboard</h2>
            <div className="grid grid-cols-1 lg:grid-cols-2 gap-8">
                <div className="bg-white shadow-lg rounded-lg overflow-hidden">
                    <div className="bg-gray-50 px-6 py-4">
                        <h3 className="text-xl font-semibold text-gray-800">Added Products</h3>
                    </div>
                    <div className="overflow-x-auto">
                        <table className="w-full">
                            <thead className="bg-gray-100">
                            <tr>
                                <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">ID</th>
                                <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Name</th>
                                <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Price</th>
                            </tr>
                            </thead>
                            <tbody className="bg-white divide-y divide-gray-200">
                            {lastProducts.map((product) => (
                                <tr key={product.id}>
                                    <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-500">{product.id}</td>
                                    <td className="px-6 py-4 whitespace-nowrap text-sm font-medium text-gray-900">{product.name}</td>
                                    <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-500">${product.price.toFixed(2)}</td>
                                </tr>
                            ))}
                            </tbody>
                        </table>
                    </div>
                </div>

                <div className="bg-white shadow-lg rounded-lg overflow-hidden">
                    <div className="bg-gray-50 px-6 py-4">
                        <h3 className="text-xl font-semibold text-gray-800">Registered Users</h3>
                    </div>
                    <div className="overflow-x-auto">
                        <table className="w-full">
                            <thead className="bg-gray-100">
                            <tr>
                                <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Name</th>
                                <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Email</th>
                                <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Role</th>
                            </tr>
                            </thead>
                            <tbody className="bg-white divide-y divide-gray-200">
                            {lastUsers.map((user) => (
                                <tr key={user.id}>
                                    <td className="px-6 py-4 whitespace-nowrap text-sm font-medium text-gray-900">{user.name}</td>
                                    <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-500">{user.email}</td>
                                    <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-500">
                                            <span className={`px-2 inline-flex text-xs leading-5 font-semibold rounded-full ${user.role === 'admin' ? 'bg-green-100 text-green-800' : 'bg-blue-100 text-blue-800'}`}>
                                                {user.role}
                                            </span>
                                    </td>
                                </tr>
                            ))}
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    )
}