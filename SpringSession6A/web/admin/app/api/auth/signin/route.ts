import { NextResponse } from 'next/server'
import { findUserByEmail, generateToken } from '@/utils/mockDB'

export async function POST(request: Request) {
    const { email, password } = await request.json()
    const user = findUserByEmail(email)

    if (user && user.password === password) {
        const token = generateToken(user)
        return NextResponse.json({ token })
    } else {
        return NextResponse.json({ error: 'Invalid credentials' }, { status: 401 })
    }
}