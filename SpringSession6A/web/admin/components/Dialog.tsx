import React, { useState, useEffect, useRef } from 'react'

interface DialogProps {
    children: React.ReactNode
    trigger: React.ReactNode
}

export function Dialog({ children, trigger }: DialogProps) {
    const [isOpen, setIsOpen] = useState(false)
    const dialogRef = useRef<HTMLDivElement>(null)

    useEffect(() => {
        const handleClickOutside = (event: MouseEvent) => {
            if (dialogRef.current && !dialogRef.current.contains(event.target as Node)) {
                setIsOpen(false)
            }
        }

        if (isOpen) {
            document.addEventListener('mousedown', handleClickOutside)
        }

        return () => {
            document.removeEventListener('mousedown', handleClickOutside)
        }
    }, [isOpen])

    return (
        <>
            <div onClick={() => setIsOpen(true)}>{trigger}</div>
            {isOpen && (
                <div className="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center p-4">
                    <div ref={dialogRef} className="bg-white rounded-lg shadow-xl max-w-md w-full">
                        {children}
                    </div>
                </div>
            )}
        </>
    )
}

export function DialogTrigger({ children }: { children: React.ReactNode }) {
    return <>{children}</>
}

export function DialogContent({ children }: { children: React.ReactNode }) {
    return <div className="p-6">{children}</div>
}

export function DialogHeader({ children }: { children: React.ReactNode }) {
    return <div className="mb-4">{children}</div>
}

export function DialogTitle({ children }: { children: React.ReactNode }) {
    return <h2 className="text-lg font-semibold">{children}</h2>
}