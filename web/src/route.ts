import { createRouter, createWebHistory } from "vue-router";

import Home from "./pages/Home.vue";

const routes = [
  { path: "/", component: Home },
  {
    path: "/auth",
    component: () => import("./pages/Auth.vue"),
  },
  {
    path: "/add",
    component: () => import("./pages/AddContact.vue"),
  },
  {
    path: "/add-group",
    component: () => import("./pages/AddGroup.vue"),
  },
];

export const router = createRouter({
  history: createWebHistory(),
  routes,
});
