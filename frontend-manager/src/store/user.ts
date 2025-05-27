import { defineStore } from 'pinia';
import { getAllUsers, createUser, updateUser, deleteUser, type User } from '@/api/user'; // Ensure User type is imported
import { ElMessage } from 'element-plus';

interface UserState {
  users: User[];
  currentUser: User | null;
  loading: boolean;
  error: string | null;
}

export const useUserStore = defineStore('user', {
  state: (): UserState => ({
    users: [],
    currentUser: null, // Could be used for viewing/editing a specific user's details page
    loading: false,
    error: null,
  }),

  getters: {
    // Example getter:
    // enabledUsers: (state) => state.users.filter(user => user.enabled),
  },

  actions: {
    async fetchUsers() {
      this.loading = true;
      this.error = null;
      try {
        this.users = await getAllUsers();
      } catch (err: any) {
        this.error = err.message || 'Failed to fetch users';
        ElMessage.error(this.error);
        console.error('Pinia fetchUsers error:', err);
      } finally {
        this.loading = false;
      }
    },

    async addUser(userData: User) {
      this.loading = true;
      this.error = null;
      try {
        const newUser = await createUser(userData);
        // Optionally add to local state or refetch all
        this.users.push(newUser); // Simple update, might need sorting or pagination logic
        ElMessage.success('User created successfully via store.');
        return newUser;
      } catch (err: any) {
        this.error = err.message || 'Failed to create user';
        ElMessage.error(this.error);
        console.error('Pinia addUser error:', err);
        throw err; // Re-throw to be caught by component if needed
      } finally {
        this.loading = false;
      }
    },

    async editUser(userId: string | number, userData: User) {
      this.loading = true;
      this.error = null;
      try {
        const updatedUserData = await updateUser(userId, userData);
        const index = this.users.findIndex(u => u.id === userId);
        if (index !== -1) {
          this.users[index] = { ...this.users[index], ...updatedUserData };
        }
        ElMessage.success('User updated successfully via store.');
        return updatedUserData;
      } catch (err: any) {
        this.error = err.message || 'Failed to update user';
        ElMessage.error(this.error);
        console.error('Pinia editUser error:', err);
        throw err;
      } finally {
        this.loading = false;
      }
    },

    async removeUser(userId: string | number) {
      this.loading = true;
      this.error = null;
      try {
        await deleteUser(userId);
        this.users = this.users.filter(u => u.id !== userId);
        ElMessage.success('User deleted successfully via store.');
      } catch (err: any) {
        this.error = err.message || 'Failed to delete user';
        ElMessage.error(this.error);
        console.error('Pinia removeUser error:', err);
        throw err;
      } finally {
        this.loading = false;
      }
    },

    // Action to select a user for detail view or editing (if needed for a separate page)
    selectUser(user: User | null) {
      this.currentUser = user;
    }
  },
});
