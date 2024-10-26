import React from 'react'

interface InputProps extends React.InputHTMLAttributes<HTMLInputElement> {
    label: string
}

export const Input: React.FC<InputProps> = ({ label, id, className = '', ...props }) => {
    return (
        <div className="mb-4">
            <label htmlFor={id} className="block text-sm font-medium text-gray-700 mb-1">
                {label}
            </label>
            <input
                id={id}
                className={`w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-blue-500 focus:border-blue-500 ${className}`}
                {...props}
            />
        </div>
    )
}