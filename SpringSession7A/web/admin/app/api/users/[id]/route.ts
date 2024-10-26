import { NextResponse } from 'next/server'
import { findUserById, verifyToken } from '@/utils/mockDB'

export async function GET(request: Request, { params }: { params: { id: string } }) {
    const token = request.headers.get('Authorization')?.split(' ')[1]

    if (!token || !verifyToken(token)) {
        return NextResponse.json({ error: 'Unauthorized' }, { status: 401 })
    }

    const user = findUserById(parseInt(params.id))

    if (user) {
        return NextResponse.json(user)
    } else {
        return NextResponse.json({ error: 'User not found' }, { status: 404 })
    }
}