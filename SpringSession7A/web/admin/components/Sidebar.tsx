import Link from 'next/link'
import { useAuth } from '@/contexts/AuthContext'

export default function Sidebar() {
    const { role } = useAuth()

    return (
        <aside className="w-64 bg-gray-800 text-white p-4 flex flex-col h-screen">
            <div className="mb-8">
                <Link href="/dashboard" className="flex items-center space-x-2">
                    <svg className="h-8 w-8" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                        <path d="M12 2L2 7L12 12L22 7L12 2Z" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round"/>
                        <path d="M2 17L12 22L22 17" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round"/>
                        <path d="M2 12L12 17L22 12" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round"/>
                    </svg>
                    <span className="text-xl font-bold">Admin</span>
                </Link>
            </div>
            <nav className="space-y-2 flex-grow">
                <Link href="/dashboard" className="flex items-center space-x-2 p-2 hover:bg-gray-700 rounded transition duration-150 ease-in-out">
                    <svg xmlns="http://www.w3.org/2000/svg" className="h-5 w-5" viewBox="0 0 20 20" fill="currentColor">
                        <path d="M10.707 2.293a1 1 0 00-1.414 0l-7 7a1 1 0 001.414 1.414L4 10.414V17a1 1 0 001 1h2a1 1 0 001-1v-2a1 1 0 011-1h2a1 1 0 011 1v2a1 1 0 001 1h2a1 1 0 001-1v-6.586l.293.293a1 1 0 001.414-1.414l-7-7z" />
                    </svg>
                    <span>Home</span>
                </Link>
                {(role === 'ADMIN' || role === 'MODERATOR') && (
                <Link href="/dashboard/products" className="flex items-center space-x-2 p-2 hover:bg-gray-700 rounded transition duration-150 ease-in-out">
                    <svg xmlns="http://www.w3.org/2000/svg" className="h-5 w-5" viewBox="0 0 20 20" fill="currentColor">
                        <path d="M4 3a2 2 0 100 4h12a2 2 0 100-4H4z" />
                        <path fillRule="evenodd" d="M3 8h14v7a2 2 0 01-2 2H5a2 2 0 01-2-2V8zm5 3a1 1 0 011-1h2a1 1 0 110 2H9a1 1 0 01-1-1z" clipRule="evenodd" />
                    </svg>
                    <span>Products</span>
                </Link>
                )}
                {role === 'ADMIN' && (
                    <Link href="/dashboard/users" className="flex items-center space-x-2 p-2 hover:bg-gray-700 rounded transition duration-150 ease-in-out">
                        <svg xmlns="http://www.w3.org/2000/svg" className="h-5 w-5" viewBox="0 0 20 20" fill="currentColor">
                            <path d="M9 6a3 3 0 11-6 0 3 3 0 016 0zM17 6a3 3 0 11-6 0 3 3 0 016 0zM12.93 17c.046-.327.07-.66.07-1a6.97 6.97 0 00-1.5-4.33A5 5 0 0119 16v1h-6.07zM6 11a5 5 0 015 5v1H1v-1a5 5 0 015-5z" />
                        </svg>
                        <span>Users</span>
                    </Link>
                )}
            </nav>
        </aside>
    )
}