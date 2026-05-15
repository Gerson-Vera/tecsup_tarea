export interface Account {
  id: string;
  accountNumber: string;
  ownerName: string;
  balance: number;
  status: 'ACTIVE' | 'INACTIVE';
  createdAt: string;
}

export interface Transaction {
  id: string;
  sourceAccountId: string;
  destinationAccountId: string;
  amount: number;
  commission: number;
  status: 'COMPLETED' | 'FAILED';
  description: string;
  createdAt: string;
}

export interface ApiError {
  error: string;
}
