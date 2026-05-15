import type { Account, Transaction } from './types';

const BASE = '/api';

async function handleResponse<T>(res: Response): Promise<T> {
  if (!res.ok) {
    const body = await res.json().catch(() => ({ error: 'Error en la solicitud' }));
    throw new Error(body.error || 'Error en la solicitud');
  }
  return res.json();
}

export async function getAccounts(): Promise<Account[]> {
  const res = await fetch(`${BASE}/accounts`);
  return handleResponse<Account[]>(res);
}

export async function getAccount(id: string): Promise<Account> {
  const res = await fetch(`${BASE}/accounts/${id}`);
  return handleResponse<Account>(res);
}

export async function getAccountBalance(id: string): Promise<{ balance: number }> {
  const res = await fetch(`${BASE}/accounts/${id}/balance`);
  return handleResponse<{ balance: number }>(res);
}

export async function createAccount(data: {
  ownerName: string;
  initialBalance: number;
}): Promise<Account> {
  const res = await fetch(`${BASE}/accounts`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(data),
  });
  return handleResponse<Account>(res);
}

export async function createTransfer(data: {
  sourceAccountId: string;
  destinationAccountId: string;
  amount: number;
  description: string;
}): Promise<Transaction> {
  const res = await fetch(`${BASE}/transfers`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(data),
  });
  return handleResponse<Transaction>(res);
}
