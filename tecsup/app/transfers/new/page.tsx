import { Suspense } from 'react';
import TransferForm from '@/components/TransferForm';

function LoadingSpinner() {
  return (
    <div className="flex justify-center items-center py-20">
      <div className="w-8 h-8 border-4 border-blue-600 border-t-transparent rounded-full animate-spin" />
    </div>
  );
}

export default function TransferPage() {
  return (
    <Suspense fallback={<LoadingSpinner />}>
      <TransferForm />
    </Suspense>
  );
}
