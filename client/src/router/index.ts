import { createRouter, createWebHistory, RouteRecordRaw } from 'vue-router'
import Library from '../views/Library.vue'
import ViewBook from '../views/ViewBook.vue'

const routes: Array<RouteRecordRaw> = [
  {
    path: '/',
    name: 'Library',
    component: Library
  },
  {
    path: '/book/:id',
    name: 'Book',
    component: ViewBook
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router
