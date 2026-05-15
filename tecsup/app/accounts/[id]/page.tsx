'use client';

import { useEffect, useState } from 'react';
import { useParams } from 'next/navigation';
import Link from 'next/link';
import { getAccount, getAccountBalance } from '@/lib/api';
import type { Account } from '@/lib/types';

export default function AccountDetailPage() {
  const { id } = useParams<{ id: string }>();
  const [account, setAccount] = useState<Account | null>(null);
  const [balance, setBalance] = useState<number | null>(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    if (!id) return;
    Promise.all([getAccount(id), getAccountBalance(id)])
      .then(([acc, bal]) => {
        setAccount(acc);
        setBalance(bal.balance);
      })
      .catch((err: unknown) =>
        setError(err instanceof Error ? err.message : 'Error al cargar la cuenta.')
      )
      .finally(() => setLoading(false));
  }, [id]);

  if (loading) {
    return (
      <div className="flex justify-center items-center py-20">
        <div className="w-8 h-8 border-4 border-blue-600 border-t-transparent rounded-full animate-spin" />
      </div>
    );
  }

  if (error) {
    return (
      <div className="max-w-lg mx-auto">
        <Link href="/" className="text-blue-600 text-sm hover:underline">
          ← Volver al dashboard
        </Link>
        <div className="mt-4 bg-red-50 border border-red-200 text-red-700 px-4 py-3 rounded-lg text-sm">
          {error}
        </div>
      </div>
    );
  }

  if (!account) return null;

  const badgeCls =
    account.status === 'ACTIVE'
      ? 'bg-green-100 text-green-700'
      : 'bg-gray-100 text-gray-500';

  const createdDate = new Date(account.createdAt).toLocaleDateString('es-PE', {
    day: '2-digit',
    month: 'long',
    year: 'numeric',
  });

  return (
    <div className="max-w-lg mx-auto">
      <div className="mb-6">
        <Link href="/" className="text-blue-600 text-sm hover:underline">
          ← Volver al dashboard
        </Link>
        <h1 className="text-2xl font-bold text-gray-900 mt-2">Detalle de cuenta</h1>
      </div>

      <div className="bg-white rounded-xl border border-gray-200 shadow-sm p-6 space-y-5">
        <div className="flex items-center justify-between">
          <h2 className="text-xl font-semibold text-gray-900">{account.ownerName}</h2>
          <span className={`text-xs font-semibold px-2 py-0.5 rounded-full ${badgeCls}`}>
            {account.status}
          </span>
        </div>

        <div className="grid grid-cols-2 gap-4 text-sm border-t pt-4">
          <div>
            <p className="text-gray-500 mb-0.5">Número de cuenta</p>
            <p className="font-mono font-medium text-gray-900">{account.accountNumber}</p>
          </div>
          <div>
            <p className="text-gray-500 mb-0.5">ID</p>
            <p className="font-mono font-medium text-gray-900 text-xs truncate">{account.id}</p>
          </div>
          <div>
            <p className="text-gray-500 mb-0.5">Fecha de creación</p>
            <p className="font-medium text-gray-900">{createdDate}</p>
          </div>
          <div>
            <p className="text-gray-500 mb-0.5">Estado</p>
            <p className="font-medium text-gray-900">{account.status}</p>
          </div>
        </div>

        <div className="border-t pt-4">
          <p className="text-sm text-gray-500">Saldo actual</p>
          <p className="text-3xl font-bold text-blue-700 mt-1">
            S/ {(balance ?? account.balance).toFixed(2)}
          </p>
        </div>

        <Link
          href={`/transfers/new?from=${account.id}`}
          className="block w-full text-center bg-blue-600 text-white py-2.5 rounded-lg font-medium text-sm hover:bg-blue-700 transition-colors"
        >
          Realizar transferencia
        </Link>
      </div>
    </div>
  );
}
