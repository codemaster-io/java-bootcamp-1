import React from 'react'
import { Label } from './Label'

interface CheckboxOption {
    id: string
    label: string
}

interface CheckboxProps {
    label: string
    options: CheckboxOption[]
    selectedOptions: string[]
    onChange: (id: string) => void
}

export function Checkbox({ label, options, selectedOptions, onChange }: CheckboxProps) {
    return (
        <div className="space-y-2">
            <Label>{label}</Label>
            <div className="space-y-2">
                {options.map((option) => (
                    <div key={option.id} className="flex items-center">
                        <input
                            type="checkbox"
                            id={option.id}
                            checked={selectedOptions.includes(option.id)}
                            onChange={() => onChange(option.id)}
                            className="h-4 w-4 text-blue-600 focus:ring-blue-500 border-gray-300 rounded"
                        />
                        <label htmlFor={option.id} className="ml-2 block text-sm text-gray-900">
                            {option.label}
                        </label>
                    </div>
                ))}
            </div>
        </div>
    )
}