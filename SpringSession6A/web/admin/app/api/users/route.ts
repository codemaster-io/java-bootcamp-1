import { NextResponse } from 'next/server'
import { getUsers, addUser, updateUser, deleteUser, verifyToken } from '@/utils/mockDB'

export async function GET(request: Request) {
    const { searchParams } = new URL(request.url)
    const token = request.headers.get('Authorization')?.split(' ')[1]

    if (!token || !verifyToken(token)) {
        return NextResponse.json({ error: 'Unauthorized' }, { status: 401 })
    }

    const page = parseInt(searchParams.get('page') || '1')
    const limit = parseInt(searchParams.get('limit') || '10')

    const { users, total } = getUsers(page, limit)
    return NextResponse.json({ users, total })
}

export async function POST(request: Request) {
    const token = request.headers.get('Authorization')?.split(' ')[1]

    if (!token || !verifyToken(token)) {
        return NextResponse.json({ error: 'Unauthorized' }, { status: 401 })
    }

    const userData = await request.json()
    const newUser = addUser(userData)
    return NextResponse.json(newUser)
}

export async function PUT(request: Request) {
    const token = request.headers.get('Authorization')?.split(' ')[1]

    if (!token || !verifyToken(token)) {
        return NextResponse.json({ error: 'Unauthorized' }, { status: 401 })
    }

    const userData = await request.json()
    const updatedUser = updateUser(userData)
    return NextResponse.json(updatedUser)
}

export async function DELETE(request: Request) {
    const token = request.headers.get('Authorization')?.split(' ')[1]

    if (!token || !verifyToken(token)) {
        return NextResponse.json({ error: 'Unauthorized' }, { status: 401 })
    }

    const { searchParams } = new URL(request.url)
    const id = parseInt(searchParams.get('id') || '')

    if (isNaN(id)) {
        return NextResponse.json({ error: 'Invalid user ID' }, { status: 400 })
    }

    const result = deleteUser(id)
    return NextResponse.json(result)
}