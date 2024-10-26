import { NextResponse } from 'next/server'
import { findProductById, verifyToken } from '@/utils/mockDB'

export async function GET(request: Request, { params }: { params: { id: string } }) {
    const token = request.headers.get('Authorization')?.split(' ')[1]

    if (!token || !verifyToken(token)) {
        return NextResponse.json({ error: 'Unauthorized' }, { status: 401 })
    }

    const product = findProductById(parseInt(params.id))

    if (product) {
        return NextResponse.json(product)
    } else {
        return NextResponse.json({ error: 'Product not found' }, { status: 404 })
    }
}