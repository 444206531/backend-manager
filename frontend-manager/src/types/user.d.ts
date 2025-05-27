// frontend/src/types/user.d.ts
export interface User {
  id?: number | string | null; 
  username: string;
  password?: string; // Optional as it's not always sent/received
  email: string;
  enabled: boolean;
  // Add any other fields that your backend User entity has
}
