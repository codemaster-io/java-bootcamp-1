import { NextResponse } from 'next/server'
import { getProducts, addProduct, updateProduct, deleteProduct, verifyToken } from '@/utils/mockDB'

export async function GET(request: Request) {
    const { searchParams } = new URL(request.url)
    const token = request.headers.get('Authorization')?.split(' ')[1]

    if (!token || !verifyToken(token)) {
        return NextResponse.json({ error: 'Unauthorized' }, { status: 401 })
    }

    const page = parseInt(searchParams.get('page') || '1')
    const limit = parseInt(searchParams.get('limit') || '10')

    const { products, total } = getProducts(page, limit)
    return NextResponse.json({ products, total })
}

export async function POST(request: Request) {
    const token = request.headers.get('Authorization')?.split(' ')[1]

    if (!token || !verifyToken(token)) {
        return NextResponse.json({ error: 'Unauthorized' }, { status: 401 })
    }

    const productData = await request.json()
    const newProduct = addProduct(productData)
    return NextResponse.json(newProduct)
}

export async function PUT(request: Request) {
    const token = request.headers.get('Authorization')?.split(' ')[1]

    if (!token || !verifyToken(token)) {
        return NextResponse.json({ error: 'Unauthorized' }, { status: 401 })
    }

    const productData = await request.json()
    const updatedProduct = updateProduct(productData)
    return NextResponse.json(updatedProduct)
}

export async function DELETE(request: Request) {
    const token = request.headers.get('Authorization')?.split(' ')[1]

    if (!token || !verifyToken(token)) {
        return NextResponse.json({ error: 'Unauthorized' }, { status: 401 })
    }

    const { searchParams } = new URL(request.url)
    const id = parseInt(searchParams.get('id') || '')

    if (isNaN(id)) {
        return NextResponse.json({ error: 'Invalid product ID' }, { status: 400 })
    }

    const result = deleteProduct(id)
    return NextResponse.json(result)
}