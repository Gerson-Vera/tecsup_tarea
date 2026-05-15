import Link from 'next/link';
import type { Account } from '@/lib/types';

interface Props {
  account: Account;
}

export default function AccountCard({ account }: Props) {
  const badgeCls =
    account.status === 'ACTIVE'
      ? 'bg-green-100 text-green-700'
      : 'bg-gray-100 text-gray-500';

  return (
    <Link href={`/accounts/${account.id}`}>
      <div className="bg-white rounded-xl border border-gray-200 p-5 hover:shadow-md hover:border-blue-300 transition-all cursor-pointer h-full">
        <div className="flex items-start justify-between mb-3">
          <div className="min-w-0 flex-1 mr-3">
            <p className="font-semibold text-gray-900 truncate">{account.ownerName}</p>
            <p className="text-xs text-gray-400 font-mono mt-0.5">{account.accountNumber}</p>
          </div>
          <span className={`shrink-0 text-xs font-semibold px-2 py-0.5 rounded-full ${badgeCls}`}>
            {account.status}
          </span>
        </div>
        <p className="text-2xl font-bold text-blue-700">
          S/ {account.balance.toFixed(2)}
        </p>
      </div>
    </Link>
  );
}
