'use client';

import { useEffect, useState } from 'react';
import { useSearchParams } from 'next/navigation';
import Link from 'next/link';
import { getAccounts, createTransfer } from '@/lib/api';
import type { Account, Transaction } from '@/lib/types';

function Spinner() {
  return (
    <div className="flex justify-center items-center py-20">
      <div className="w-8 h-8 border-4 border-blue-600 border-t-transparent rounded-full animate-spin" />
    </div>
  );
}

export default function TransferForm() {
  const searchParams = useSearchParams();
  const fromId = searchParams.get('from') ?? '';

  const [accounts, setAccounts] = useState<Account[]>([]);
  const [accountsLoading, setAccountsLoading] = useState(true);
  const [sourceId, setSourceId] = useState(fromId);
  const [destinationId, setDestinationId] = useState('');
  const [amount, setAmount] = useState('');
  const [description, setDescription] = useState('');
  const [submitting, setSubmitting] = useState(false);
  const [error, setError] = useState<string | null>(null);
  const [result, setResult] = useState<Transaction | null>(null);

  useEffect(() => {
    getAccounts()
      .then(setAccounts)
      .catch(() => setError('No se pudieron cargar las cuentas.'))
      .finally(() => setAccountsLoading(false));
  }, []);

  const destinationOptions = accounts.filter((a) => a.id !== sourceId);

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setError(null);

    const amt = parseFloat(amount);
    if (!sourceId || !destinationId) {
      setError('Selecciona las cuentas origen y destino.');
      return;
    }
    if (isNaN(amt) || amt <= 0) {
      setError('El monto debe ser mayor a 0.');
      return;
    }

    setSubmitting(true);
    try {
      const tx = await createTransfer({
        sourceAccountId: sourceId,
        destinationAccountId: destinationId,
        amount: amt,
        description: description.trim() || 'Transferencia',
      });
      setResult(tx);
    } catch (err: unknown) {
      setError(err instanceof Error ? err.message : 'Error al realizar la transferencia.');
    } finally {
      setSubmitting(false);
    }
  };

  if (result) {
    const isCompleted = result.status === 'COMPLETED';
    const boxCls = isCompleted
      ? 'bg-green-50 border-green-200 text-green-800'
      : 'bg-red-50 border-red-200 text-red-800';

    return (
      <div className="max-w-md mx-auto">
        <h1 className="text-2xl font-bold text-gray-900 mb-6">Resultado de la transferencia</h1>
        <div className={`rounded-xl border p-6 space-y-3 ${boxCls}`}>
          <div className="flex items-center justify-between">
            <span className="font-medium">Estado</span>
            <span className="font-bold">{result.status}</span>
          </div>
          <div className="flex items-center justify-between border-t pt-3">
            <span className="font-medium">Monto</span>
            <span>S/ {result.amount.toFixed(2)}</span>
          </div>
          <div className="flex items-center justify-between">
            <span className="font-medium">Comisión</span>
            <span>S/ {result.commission.toFixed(2)}</span>
          </div>
          <div className="flex items-center justify-between">
            <span className="font-medium">Descripción</span>
            <span className="text-right max-w-[60%] truncate">{result.description}</span>
          </div>
        </div>
        <div className="mt-4 flex gap-3">
          <button
            onClick={() => {
              setResult(null);
              setAmount('');
              setDescription('');
              setError(null);
            }}
            className="flex-1 border border-blue-600 text-blue-600 py-2 rounded-lg text-sm font-medium hover:bg-blue-50 transition-colors"
          >
            Nueva transferencia
          </button>
          <Link
            href="/"
            className="flex-1 text-center bg-blue-600 text-white py-2 rounded-lg text-sm font-medium hover:bg-blue-700 transition-colors"
          >
            Ir al dashboard
          </Link>
        </div>
      </div>
    );
  }

  return (
    <div className="max-w-md mx-auto">
      <div className="mb-6">
        <Link href="/" className="text-blue-600 text-sm hover:underline">
          ← Volver al dashboard
        </Link>
        <h1 className="text-2xl font-bold text-gray-900 mt-2">Nueva transferencia</h1>
      </div>

      {accountsLoading ? (
        <Spinner />
      ) : (
        <div className="bg-white rounded-xl border border-gray-200 shadow-sm p-6">
          <form onSubmit={handleSubmit} className="space-y-5">
            <div>
              <label className="block text-sm font-medium text-gray-700 mb-1">
                Cuenta origen
              </label>
              <select
                value={sourceId}
                onChange={(e) => {
                  setSourceId(e.target.value);
                  setDestinationId('');
                }}
                className="w-full border border-gray-300 rounded-lg px-3 py-2 text-sm focus:outline-none focus:ring-2 focus:ring-blue-500 bg-white"
              >
                <option value="">Selecciona una cuenta</option>
                {accounts.map((a) => (
                  <option key={a.id} value={a.id}>
                    {a.ownerName} — {a.accountNumber} (S/ {a.balance.toFixed(2)})
                  </option>
                ))}
              </select>
            </div>

            <div>
              <label className="block text-sm font-medium text-gray-700 mb-1">
                Cuenta destino
              </label>
              <select
                value={destinationId}
                onChange={(e) => setDestinationId(e.target.value)}
                disabled={!sourceId}
                className="w-full border border-gray-300 rounded-lg px-3 py-2 text-sm focus:outline-none focus:ring-2 focus:ring-blue-500 bg-white disabled:bg-gray-50 disabled:text-gray-400"
              >
                <option value="">Selecciona una cuenta</option>
                {destinationOptions.map((a) => (
                  <option key={a.id} value={a.id}>
                    {a.ownerName} — {a.accountNumber}
                  </option>
                ))}
              </select>
            </div>

            <div>
              <label className="block text-sm font-medium text-gray-700 mb-1">Monto</label>
              <div className="relative">
                <span className="absolute left-3 top-1/2 -translate-y-1/2 text-gray-500 text-sm select-none">
                  S/
                </span>
                <input
                  type="number"
                  min="0.01"
                  step="0.01"
                  value={amount}
                  onChange={(e) => setAmount(e.target.value)}
                  placeholder="0.00"
                  className="w-full border border-gray-300 rounded-lg pl-8 pr-3 py-2 text-sm focus:outline-none focus:ring-2 focus:ring-blue-500"
                />
              </div>
            </div>

            <div>
              <label className="block text-sm font-medium text-gray-700 mb-1">Descripción</label>
              <input
                type="text"
                value={description}
                onChange={(e) => setDescription(e.target.value)}
                placeholder="Ej: Pago de servicios"
                className="w-full border border-gray-300 rounded-lg px-3 py-2 text-sm focus:outline-none focus:ring-2 focus:ring-blue-500"
              />
            </div>

            <div className="bg-blue-50 border border-blue-100 rounded-lg px-4 py-3 text-sm text-blue-700">
              Se aplicará una comisión de <strong>S/ 5.00</strong>
            </div>

            {error && (
              <div className="bg-red-50 border border-red-200 text-red-700 px-3 py-2 rounded-lg text-sm">
                {error}
              </div>
            )}

            <button
              type="submit"
              disabled={submitting}
              className="w-full bg-blue-600 text-white py-2 rounded-lg font-medium text-sm hover:bg-blue-700 disabled:opacity-50 disabled:cursor-not-allowed transition-colors"
            >
              {submitting ? 'Enviando...' : 'Realizar transferencia'}
            </button>
          </form>
        </div>
      )}
    </div>
  );
}
