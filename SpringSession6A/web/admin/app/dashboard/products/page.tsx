'use client'

import { useState, useEffect } from 'react'
import Link from 'next/link'
import { useAuth } from '@/contexts/AuthContext'
import { Button } from '@/components/Button'
import { Input } from '@/components/Input'
import { Modal } from '@/components/Modal'

interface Product {
    id: number
    name: string
    price: number
    description: string
    addedByUserEmail: string
}

export default function Products() {
    const [products, setProducts] = useState<Product[]>([])
    const [currentPage, setCurrentPage] = useState(1)
    const [totalPages, setTotalPages] = useState(1)
    const [isAddingProduct, setIsAddingProduct] = useState(false)
    const [isEditingProduct, setIsEditingProduct] = useState(false)
    const [editingProduct, setEditingProduct] = useState<Product | null>(null)
    const [newProduct, setNewProduct] = useState({ name: '', price: '', description: ''})
    const { token } = useAuth()

    useEffect(() => {
        const fetchProducts = async () => {
            const baseUrl = process.env.NEXT_PUBLIC_API_BASE_URL || '';
            console.log("token: ", token)
            try {
                const response = await fetch(`${baseUrl}/api/products`, {
                    method: 'GET',
                    headers: { 'Authorization': `Bearer ${token}` }
                })
                const data = await response.json()
                setProducts(data.products)
                setTotalPages(Math.ceil(data.total / 10))
            } catch (error) {
                console.error('Error fetching products:', error)
            }
        }

        if (token) {
            fetchProducts()
        }
    }, [currentPage, token])

    const handleAddProduct = async (e: React.FormEvent) => {
        e.preventDefault()
        const baseUrl = process.env.NEXT_PUBLIC_API_BASE_URL || '';

        try {
            const response = await fetch(`${baseUrl}/api/products`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${token}`
                },
                body: JSON.stringify({
                    ...newProduct,
                    price: parseFloat(newProduct.price),
                })
            })
            if (response.ok) {
                const data = await response.json()
                setProducts(prevProducts => [...prevProducts, data.product])
                setIsAddingProduct(false)
                setNewProduct({ name: '', price: '', description: '' })
            } else {
                console.error('Failed to add product')
            }
        } catch (error) {
            console.error('Error adding product:', error)
        }
    }

    const handleEditProduct = async (e: React.FormEvent) => {
        e.preventDefault()
        if (!editingProduct) return
        const baseUrl = process.env.NEXT_PUBLIC_API_BASE_URL || '';

        try {
            const response = await fetch(`${baseUrl}/api/products`, {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${token}`
                },
                body: JSON.stringify({
                    ...editingProduct,
                    price: parseFloat(editingProduct.price.toString()),
                })
            })
            if (response.ok) {
                const data = await response.json()
                setProducts(products.map(product => product.id === data.product.id ? data.product : product))
                setIsEditingProduct(false)
                setEditingProduct(null)
            } else {
                console.error('Failed to update product')
            }
        } catch (error) {
            console.error('Error updating product:', error)
        }
    }

    const handleDeleteProduct = async (productId: number) => {
        if (window.confirm('Are you sure you want to delete this product?')) {
            const baseUrl = process.env.NEXT_PUBLIC_API_BASE_URL || '';

            try {
                const response = await fetch(`${baseUrl}/api/products/${productId}`, {
                    method: 'DELETE',
                    headers: { 'Authorization': `Bearer ${token}` }
                })
                if (response.ok) {
                    setProducts(products.filter(product => product.id !== productId))
                } else {
                    console.error('Failed to delete product')
                }
            } catch (error) {
                console.error('Error deleting product:', error)
            }
        }
    }

    return (
        <div className="space-y-6">
            <h2 className="text-2xl font-bold">Products</h2>
            <Button onClick={() => setIsAddingProduct(true)}>Add New Product</Button>
            <Modal isOpen={isAddingProduct} onClose={() => setIsAddingProduct(false)} title="Add New Product">
                <form onSubmit={handleAddProduct} className="space-y-4">
                    <Input
                        label="Name"
                        value={newProduct.name}
                        onChange={(e) => setNewProduct({ ...newProduct, name: e.target.value })}
                        required
                    />
                    <Input
                        label="Price"
                        type="number"
                        value={newProduct.price}
                        onChange={(e) => setNewProduct({ ...newProduct, price: e.target.value })}
                        required
                    />
                    <Input
                        label="Description"
                        value={newProduct.description}
                        onChange={(e) => setNewProduct({ ...newProduct, description: e.target.value })}
                        required
                    />
                    <Button type="submit">Add Product</Button>
                </form>
            </Modal>
            <Modal isOpen={isEditingProduct} onClose={() => setIsEditingProduct(false)} title="Edit Product">
                {editingProduct && (
                    <form onSubmit={handleEditProduct} className="space-y-4">
                        <Input
                            label="Name"
                            value={editingProduct.name}
                            onChange={(e) => setEditingProduct({ ...editingProduct, name: e.target.value })}
                            required
                        />
                        <Input
                            label="Price"
                            type="number"
                            value={editingProduct.price}
                            onChange={(e) => setEditingProduct({ ...editingProduct, price: parseFloat(e.target.value) })}
                            required
                        />
                        <Input
                            label="Description"
                            value={editingProduct.description}
                            onChange={(e) => setEditingProduct({ ...editingProduct, description: e.target.value })}
                            required
                        />
                        <Button type="submit">Update Product</Button>
                    </form>
                )}
            </Modal>
            <div className="bg-white shadow overflow-hidden sm:rounded-lg">
                <table className="min-w-full divide-y divide-gray-200">
                    <thead className="bg-gray-50">
                    <tr>
                        <th scope="col" className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">ID</th>
                        <th scope="col" className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Name</th>
                        <th scope="col" className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Price</th>
                        <th scope="col" className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">AddedBy</th>
                        <th scope="col" className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Actions</th>
                    </tr>
                    </thead>
                    <tbody className="bg-white divide-y divide-gray-200">
                    {products.map((product) => (
                        <tr key={product.id}>
                            <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-500">{product.id}</td>
                            <td className="px-6 py-4 whitespace-nowrap text-sm font-medium text-gray-900">
                                <Link href={`/dashboard/products/${product.id}`} className="text-blue-600 hover:text-blue-800 hover:underline">
                                    {product.name}
                                </Link>
                            </td>
                            <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-500">
                                <Link href={`/dashboard/products/${product.id}`} className="text-blue-600 hover:text-blue-800 hover:underline">
                                    ${product.price}
                                </Link>
                            </td>

                            <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-500">
                                <Link href={`/dashboard/products/${product.id}`} className="text-blue-600 hover:text-blue-800 hover:underline">
                                    {product.addedByUserEmail}
                                </Link>
                            </td>

                            <td className="px-6 py-4 whitespace-nowrap text-sm font-medium">
                                <Button variant="secondary" size="sm" onClick={() => {
                                    setEditingProduct(product)
                                    setIsEditingProduct(true)
                                }}>Edit</Button>
                                <Button variant="secondary" size="sm" className="ml-2" onClick={() => handleDeleteProduct(product.id)}>Delete</Button>
                            </td>
                        </tr>
                    ))}
                    </tbody>
                </table>
            </div>
        </div>
    )
}