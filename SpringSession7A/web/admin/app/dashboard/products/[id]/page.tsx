'use client'

import { useState, useEffect } from 'react'
import { useParams } from 'next/navigation'
import { useAuth } from '@/contexts/AuthContext'

export default function ProductDetails() {
    const { id } = useParams()
    const [product, setProduct] = useState(null)
    const { token } = useAuth()

    useEffect(() => {
        const fetchProduct = async () => {
            const baseUrl = process.env.NEXT_PUBLIC_API_BASE_URL || '';

            try {
                const response = await fetch(`${baseUrl}/api/products/${id}`, {
                    headers: { 'Authorization': `Bearer ${token}` }
                })
                const data = await response.json()
                setProduct(data)
            } catch (error) {
                console.error('Error fetching product details:', error)
            }
        }

        if (token) {
            fetchProduct()
        }
    }, [id, token])

    if (!product) return <div className="text-center">Loading...</div>

    return (
        <div className="bg-white shadow overflow-hidden sm:rounded-lg">
            <div className="px-4 py-5 sm:px-6">
                <h3 className="text-lg leading-6 font-medium text-gray-900">{product.name}</h3>
            </div>
            <div className="border-t border-gray-200">
                <dl>
                    <div className="bg-white px-4 py-5 sm:grid sm:grid-cols-3 sm:gap-4 sm:px-6">
                        <dt className="text-sm font-medium text-gray-500">Description</dt>
                        <dd className="mt-1 text-sm text-gray-900 sm:mt-0 sm:col-span-2">{product.description}</dd>
                    </div>

                    <div className="bg-gray-50 px-4 py-5 sm:grid sm:grid-cols-3 sm:gap-4 sm:px-6">
                        <dt className="text-sm font-medium text-gray-500">Price</dt>
                        <dd className="mt-1 text-sm text-gray-900 sm:mt-0 sm:col-span-2">${product.price.toFixed(2)}</dd>
                    </div>

                </dl>
            </div>
            {/*<div className="px-4 py-5 sm:px-6">*/}
            {/*    <img src={product.image} alt={product.name} className="w-full h-64 object-cover rounded-lg" />*/}
            {/*</div>*/}
        </div>
    )
}