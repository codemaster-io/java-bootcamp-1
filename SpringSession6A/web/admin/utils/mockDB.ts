import { sign, verify } from 'jsonwebtoken'

const SECRET_KEY = '3245jljlfjladfuu7293845k3245jljlfjladfuu7293845k'

interface User {
    id: number
    name: string
    email: string
    password: string
    role: string
    permissions: string[]
}

interface Product {
    id: number
    name: string
    price: number
    description: string
}

let users: User[] = [
    { id: 1, name: 'Admin User', email: 'admin@gmail.com', password: 'admin123', role: 'ADMIN', permissions: ['ADMIN:READ_PERMISSION', 'ADMIN:WRITE_PERMISSION'] },
    { id: 2, name: 'General User', email: 'user@gmail.com', password: 'user123', role: 'USER', permissions: [] },
    { id: 3, name: 'Manager User', email: 'manager@gmail.com', password: 'manager123', role: 'MANAGER', permissions: ['MANAGER:READ_PERMISSION'] },
]

let products: Product[] = [
    { id: 1, name: 'Product 1', price: 19.99, description: 'This is product 1' },
    { id: 2, name: 'Product 2', price: 29.99, description: 'This is product 2'},
]

export function generateToken(user: User): string {
    return sign({ userId: user.id, email: user.email, role: user.role, userName: user.name}, SECRET_KEY, { expiresIn: '1h' })
}

export function verifyToken(token: string): { userId: number, email: string } | null {
    try {
        return verify(token, SECRET_KEY) as { userId: number, email: string, role: string}
    } catch (error) {
        return null
    }
}

export function findUserByEmail(email: string): User | undefined {
    return users.find(user => user.email === email)
}

export function findUserById(id: number): User | undefined {
    return users.find(user => user.id === id)
}

export function getUsers(page: number, limit: number): { users: User[], total: number } {
    const start = (page - 1) * limit
    const paginatedUsers = users.slice(start, start + limit)
    return { users: paginatedUsers, total: users.length }
}

export function getProducts(page: number, limit: number): { products: Product[], total: number } {
    console.log(products.length)
    const start = (page - 1) * limit
    const paginatedProducts = products.slice(start, start + limit)
    return { products: paginatedProducts, total: products.length }
}

export function findProductById(id: number): Product | undefined {
    return products.find(product => product.id === id)
}

export function addUser(user: Omit<User, 'id'>): User {
    const newUser = { ...user, id: users.length + 1 }
    users.push(newUser)
    return newUser
}

export function addProduct(product: Omit<Product, 'id'>): Product {
    const newProduct = { ...product, id: products.length + 1 }
    products.push(newProduct)
    return newProduct
}

export function updateUser(updatedUser: User): User | null {
    const index = users.findIndex(user => user.id === updatedUser.id)
    if (index !== -1) {
        users[index] = { ...users[index], ...updatedUser }
        return users[index]
    }
    return null
}

export function deleteUser(id: number): boolean {
    const initialLength = users.length
    users = users.filter(user => user.id !== id)
    return users.length < initialLength
}

export function updateProduct(updatedProduct: Product): Product | null {
    const index = products.findIndex(product => product.id === updatedProduct.id)
    if (index !== -1) {
        products[index] = { ...products[index], ...updatedProduct }
        return products[index]
    }
    return null
}

export function deleteProduct(id: number): boolean {
    const initialLength = products.length
    products = products.filter(product => product.id !== id)
    return products.length < initialLength
}