<template>
  <el-container class="app-layout">
    <el-aside width="200px" class="app-layout__sidebar">
      <el-scrollbar>
        <el-menu :default-openeds="['1']" :router="true">
          <el-sub-menu index="1">
            <template #title>
              <el-icon><Setting /></el-icon>Management
            </template>
            <el-menu-item index="/">
              <el-icon><HomeFilled /></el-icon>Home
            </el-menu-item>
            <el-menu-item index="/users">
              <el-icon><User /></el-icon>User Management
            </el-menu-item>
          </el-sub-menu>
          <!-- Add more top-level menu items or sub-menus as needed -->
        </el-menu>
      </el-scrollbar>
    </el-aside>

    <el-container>
      <el-header class="app-layout__header">
        <span>Backend Admin System</span>
        <!-- Add user dropdown or other header content here -->
      </el-header>

      <el-main class="app-layout__main-content">
        <router-view /> <!-- Page content will be rendered here -->
      </el-main>
    </el-container>
  </el-container>
</template>

<script setup lang="ts">
// Import icons from Element Plus.
// These will be auto-imported by unplugin-vue-components if configured correctly in vite.config.ts
// (which we did by adding ElementPlusResolver).
// However, explicitly importing them can sometimes help with editor intellisense or if auto-import has issues.
// For this task, assume auto-import works for icons like <Setting />, <User />, <HomeFilled />.
// If icons don't render, explicit imports might be needed:
// import { Setting, User, HomeFilled } from '@element-plus/icons-vue'

// No specific script logic needed for this basic layout yet.
</script>

<style scoped>
.app-layout {
  height: 100vh; /* Full viewport height */
}

.app-layout__sidebar {
  background-color: #f4f6f8; /* Light grey background for sidebar */
  border-right: 1px solid #e0e0e0;
}

.app-layout__header {
  background-color: #ffffff; /* White background for header */
  border-bottom: 1px solid #e0e0e0;
  display: flex;
  align-items: center;
  /* justify-content: space-between; */ /* If you have items on the right */
}

.app-layout__main-content {
  padding: 20px;
  background-color: #e9eef3; /* Light background for main content area */
}

/* Optional: Style for el-menu */
.el-menu {
  border-right: none; /* Remove default border from el-menu inside sidebar */
}
</style>
