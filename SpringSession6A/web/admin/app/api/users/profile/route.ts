import { NextResponse } from 'next/server'
import { findUserById, verifyToken } from '@/utils/mockDB'

export async function GET(request: Request) {
    const token = request.headers.get('Authorization')?.split(' ')[1]

    if (!token) {
        return NextResponse.json({ error: 'Unauthorized' }, { status: 401 })
    }

    const decodedToken = verifyToken(token)

    if (!decodedToken) {
        return NextResponse.json({ error: 'Invalid token' }, { status: 401 })
    }

    const user = findUserById(decodedToken.userId)

    if (user) {
        // Return user data without sensitive information
        const { id, name, email, role } = user
        return NextResponse.json({ id, name, email, role })
    } else {
        return NextResponse.json({ error: 'User not found' }, { status: 404 })
    }
}