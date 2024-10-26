import React, { useState, useRef, useEffect } from 'react'

interface SelectOption {
    value: string
    label: string
}

interface SelectProps {
    options: SelectOption[]
    value: string
    onChange: (value: string) => void
    placeholder?: string
}

export function Select({ options, value, onChange, placeholder = 'Select an option' }: SelectProps) {
    const [isOpen, setIsOpen] = useState(false)
    const selectRef = useRef<HTMLDivElement>(null)

    useEffect(() => {
        const handleClickOutside = (event: MouseEvent) => {
            if (selectRef.current && !selectRef.current.contains(event.target as Node)) {
                setIsOpen(false)
            }
        }

        document.addEventListener('mousedown', handleClickOutside)
        return () => {
            document.removeEventListener('mousedown', handleClickOutside)
        }
    }, [])

    const selectedOption = options.find(option => option.value === value)

    return (
        <div className="relative" ref={selectRef}>
            <div
                className="bg-white border border-gray-300 rounded-md px-4 py-2 flex items-center justify-between cursor-pointer"
                onClick={() => setIsOpen(!isOpen)}
            >
                <span className="text-sm">{selectedOption ? selectedOption.label : placeholder}</span>
                <svg className="h-5 w-5 text-gray-400" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                    <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M19 9l-7 7-7-7" />
                </svg>
            </div>
            {isOpen && (
                <div className="absolute z-10 w-full bg-white border border-gray-300 mt-1 rounded-md shadow-lg">
                    {options.map((option) => (
                        <div
                            key={option.value}
                            className="px-4 py-2 text-sm hover:bg-gray-100 cursor-pointer"
                            onClick={() => {
                                onChange(option.value)
                                setIsOpen(false)
                            }}
                        >
                            {option.label}
                        </div>
                    ))}
                </div>
            )}
        </div>
    )
}

export function SelectTrigger({ children }: { children: React.ReactNode }) {
    return <div>{children}</div>
}

export function SelectValue({ placeholder }: { placeholder: string }) {
    return <span>{placeholder}</span>
}

export function SelectContent({ children }: { children: React.ReactNode }) {
    return <div>{children}</div>
}

export function SelectItem({ value, children }: { value: string; children: React.ReactNode }) {
    return <div>{children}</div>
}