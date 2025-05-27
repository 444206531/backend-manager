import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
// If AppLayout is the one defining overall structure and contains router-view,
// then routes are typically defined as is.
// If AppLayout is meant to be a layout for a *group* of routes,
// then it would be a component in the route definition itself.
// Given current App.vue renders AppLayout, and AppLayout has router-view,
// the current flat structure is fine.

const routes = [
  {
    path: '/',
    name: 'home',
    component: HomeView,
    meta: { title: 'Home' } // Optional: for breadcrumbs or document title
  },
  {
    path: '/users',
    name: 'users',
    // route level code-splitting
    // this generates a separate chunk (UserView.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import('../views/user/UserView.vue'),
    meta: { title: 'User Management' } // Optional
  }
  // Add other routes here
]

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes
})

// Optional: Navigation Guard to update document title
router.beforeEach((to, from, next) => {
  document.title = to.meta.title ? `Admin - ${to.meta.title}` : 'Admin System';
  next();
});

export default router
