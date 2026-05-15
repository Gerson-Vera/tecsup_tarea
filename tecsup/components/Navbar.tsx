'use client';

import Link from 'next/link';
import { usePathname } from 'next/navigation';

export default function Navbar() {
  const pathname = usePathname();

  const linkCls = (href: string) =>
    `text-sm font-medium transition-colors ${
      pathname === href
        ? 'text-white border-b-2 border-white pb-0.5'
        : 'text-blue-200 hover:text-white'
    }`;

  return (
    <nav className="bg-blue-700 shadow-md">
      <div className="max-w-6xl mx-auto px-4 h-16 flex items-center justify-between">
        <Link href="/" className="text-white text-xl font-bold tracking-tight">
          Banco Digital
        </Link>
        <div className="flex items-center gap-6">
          <Link href="/" className={linkCls('/')}>
            Dashboard
          </Link>
          <Link href="/transfers/new" className={linkCls('/transfers/new')}>
            Nueva Transferencia
          </Link>
        </div>
      </div>
    </nav>
  );
}
