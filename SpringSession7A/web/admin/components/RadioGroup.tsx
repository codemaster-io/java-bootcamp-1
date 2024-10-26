import React from 'react'
import { Label } from './Label'

interface RadioOption {
    id: string
    label: string
    value: string
}

interface RadioGroupProps {
    label: string
    options: RadioOption[]
    selectedValue: string
    onChange: (value: string) => void
    name: string
}

interface RadioGroupItemProps {
    id: string
    value: string
    label: string
    checked: boolean
    onChange: () => void
    name: string
}

export function RadioGroupItem({ id, value, label, checked, onChange, name }: RadioGroupItemProps) {
    return (
        <div className="flex items-center">
            <input
                type="radio"
                id={id}
                name={name}
                value={value}
                checked={checked}
                onChange={onChange}
                className="h-4 w-4 text-blue-600 focus:ring-blue-500 border-gray-300"
            />
            <label htmlFor={id} className="ml-2 block text-sm text-gray-900">
                {label}
            </label>
        </div>
    )
}

export function RadioGroup({ label, options, selectedValue, onChange, name }: RadioGroupProps) {
    return (
        <div className="space-y-2">
            <Label>{label}</Label>
            <div className="space-y-2">
                {options.map((option) => (
                    <RadioGroupItem
                        key={option.id}
                        id={option.id}
                        name={name}
                        value={option.value}
                        label={option.label}
                        checked={selectedValue === option.value}
                        onChange={() => onChange(option.value)}
                    />
                ))}
            </div>
        </div>
    )
}