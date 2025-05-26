<template>
  <div class="user-management-view">
    <h2>User Management</h2>

    <el-card class="box-card">
      <div class="table-controls">
        <el-button type="primary" @click="handleAddUser">
          <el-icon><Plus /></el-icon> Add User
        </el-button>
      </div>

      <el-table :data="users" v-loading="loading" style="width: 100%">
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column prop="username" label="Username" />
        <el-table-column prop="email" label="Email" />
        <el-table-column prop="enabled" label="Enabled" width="100">
          <template #default="scope">
            <el-tag :type="scope.row.enabled ? 'success' : 'danger'">
              {{ scope.row.enabled ? 'Yes' : 'No' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column label="Actions" width="180">
          <template #default="scope">
            <el-button size="small" @click="handleEditUser(scope.row)">
              <el-icon><Edit /></el-icon> Edit
            </el-button>
            <el-button size="small" type="danger" @click="handleDeleteUser(scope.row.id)">
              <el-icon><Delete /></el-icon> Delete
            </el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <!-- Add/Edit User Dialog -->
    <el-dialog
      v-model="dialogVisible"
      :title="isEditMode ? 'Edit User' : 'Add User'"
      width="30%"
      @close="resetForm"
    >
      <el-form :model="currentUserForm" ref="userFormRef" label-width="100px" :rules="formRules">
        <el-form-item label="Username" prop="username">
          <el-input v-model="currentUserForm.username" />
        </el-form-item>
        <el-form-item label="Email" prop="email">
          <el-input v-model="currentUserForm.email" />
        </el-form-item>
        <el-form-item label="Password" prop="password" v-if="!isEditMode">
          <el-input type="password" v-model="currentUserForm.password" show-password />
        </el-form-item>
         <el-form-item label="Confirm Password" prop="confirmPassword" v-if="!isEditMode">
          <el-input type="password" v-model="currentUserForm.confirmPassword" show-password />
        </el-form-item>
        <el-form-item label="Enabled" prop="enabled">
          <el-switch v-model="currentUserForm.enabled" />
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="dialogVisible = false">Cancel</el-button>
          <el-button type="primary" @click="submitUserForm">
            {{ isEditMode ? 'Save Changes' : 'Create User' }}
          </el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted, reactive, nextTick } from 'vue';
import type { FormInstance, FormRules } from 'element-plus';
import { ElMessage, ElMessageBox } from 'element-plus';
// Icons (assuming auto-import works, otherwise import them explicitly)
// import { Plus, Edit, Delete } from '@element-plus/icons-vue'; 
import { getAllUsers, createUser, updateUser, deleteUser, type User } from '@/api/user'; // Adjust path if needed

const users = ref<User[]>([]);
const loading = ref(true);
const dialogVisible = ref(false);
const isEditMode = ref(false);
const userFormRef = ref<FormInstance>();

const initialUserFormState: User & { confirmPassword?: string } = {
  id: null,
  username: '',
  email: '',
  password: '',
  confirmPassword: '',
  enabled: true,
};
const currentUserForm = reactive<User & { confirmPassword?: string }>({ ...initialUserFormState });

// Form validation rules
const validatePass = (rule: any, value: any, callback: any) => {
  if (!isEditMode.value && value === '') {
    callback(new Error('Please input the password'));
  } else {
    if (currentUserForm.confirmPassword !== '') {
      if (!userFormRef.value) return;
      userFormRef.value.validateField('confirmPassword', () => null);
    }
    callback();
  }
};
const validatePass2 = (rule: any, value: any, callback: any) => {
  if (!isEditMode.value && value === '') {
    callback(new Error('Please input the confirm password'));
  } else if (!isEditMode.value && value !== currentUserForm.password) {
    callback(new Error("Passwords don't match!"));
  } else {
    callback();
  }
};

const formRules = reactive<FormRules>({
  username: [{ required: true, message: 'Please input username', trigger: 'blur' }],
  email: [
    { required: true, message: 'Please input email', trigger: 'blur' },
    { type: 'email', message: 'Please input correct email address', trigger: ['blur', 'change'] },
  ],
  password: [{ validator: validatePass, trigger: 'blur' }],
  confirmPassword: [{ validator: validatePass2, trigger: 'blur' }],
  enabled: [{ type: 'boolean' }],
});


const fetchUsers = async () => {
  loading.value = true;
  try {
    users.value = await getAllUsers();
  } catch (error) {
    console.error('Failed to fetch users:', error);
    ElMessage.error('Failed to load users.');
  } finally {
    loading.value = false;
  }
};

onMounted(fetchUsers);

const resetForm = () => {
  Object.assign(currentUserForm, initialUserFormState);
  isEditMode.value = false;
  // Delay resetting validation to ensure form is truly reset
  nextTick(() => {
    userFormRef.value?.clearValidate();
    userFormRef.value?.resetFields(); // This might be more effective
  });
};

const handleAddUser = () => {
  resetForm();
  dialogVisible.value = true;
};

const handleEditUser = (user: User) => {
  resetForm(); // Reset first to clear any previous state/validation
  isEditMode.value = true;
  // Assign editable fields. Be careful not to assign the reactive proxy directly if issues arise.
  Object.assign(currentUserForm, {
      id: user.id,
      username: user.username,
      email: user.email,
      enabled: user.enabled,
      password: '', // Password is not fetched and should be handled separately if change is needed
      confirmPassword: ''
  });
  dialogVisible.value = true;
};

const submitUserForm = async () => {
  if (!userFormRef.value) return;
  await userFormRef.value.validate(async (valid) => {
    if (valid) {
      try {
        const userDataToSubmit: User = {
          id: currentUserForm.id,
          username: currentUserForm.username,
          email: currentUserForm.email,
          enabled: currentUserForm.enabled,
        };
        if (!isEditMode.value && currentUserForm.password) {
          userDataToSubmit.password = currentUserForm.password;
        }
        // Note: For editing, password change should ideally be a separate form/process

        if (isEditMode.value) {
          await updateUser(currentUserForm.id!, userDataToSubmit);
          ElMessage.success('User updated successfully.');
        } else {
          await createUser(userDataToSubmit);
          ElMessage.success('User created successfully.');
        }
        dialogVisible.value = false;
        fetchUsers(); // Refresh table data
      } catch (error) {
        console.error('Failed to save user:', error);
        ElMessage.error('Failed to save user.');
      }
    } else {
      ElMessage.error('Form validation failed. Please check inputs.');
      return false;
    }
  });
};

const handleDeleteUser = async (userId: number | string) => {
  try {
    await ElMessageBox.confirm(
      'Are you sure you want to delete this user?',
      'Warning',
      {
        confirmButtonText: 'OK',
        cancelButtonText: 'Cancel',
        type: 'warning',
      }
    );
    await deleteUser(userId);
    ElMessage.success('User deleted successfully.');
    fetchUsers(); // Refresh table data
  } catch (error) {
    // If error is 'cancel', it means user clicked Cancel in ElMessageBox
    if (error !== 'cancel') {
      console.error('Failed to delete user:', error);
      ElMessage.error('Failed to delete user.');
    }
  }
};

// Make sure icons like <Plus />, <Edit />, <Delete /> are available
// (auto-imported by unplugin-vue-components or explicitly imported)
</script>

<style scoped>
.user-management-view {
  padding: 20px;
}
.table-controls {
  margin-bottom: 16px;
  display: flex;
  justify-content: flex-end;
}
.box-card {
  margin-top: 20px;
}
</style>
