import { NextResponse } from 'next/server'
import { addUser, generateToken } from '@/utils/mockDB'

export async function POST(request: Request) {
  const userData = await request.json()
  const newUser = addUser(userData)
  const token = generateToken(newUser)
  return NextResponse.json({ token })
}