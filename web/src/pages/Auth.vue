<script setup lang="ts">
import { ref, reactive } from 'vue';
import axios from 'axios';
import { useRouter } from 'vue-router';
const router = useRouter()

const isLogin = ref(true);
const loading = ref(false);
const message = ref({ text: '', isError: false });

const form = reactive({
    username: '',
    password: '',
    email: '' // Only for Signup
});

const handleSubmit = async () => {
    loading.value = true;
    message.value = { text: '', isError: false };

    const url = isLogin.value
        ? 'http://localhost:8080/api/auth/signin'
        : 'http://localhost:8080/api/auth/signup';

    try {
        const response = await axios.post(url, {
            username: form.username,
            password: form.password,
            ...(!isLogin.value && { email: form.email })
        });

        if (isLogin.value) {
            // Save JWT to LocalStorage
            localStorage.setItem('token', response.data.token);
            message.value = { text: 'Login Successful! Redirecting...', isError: false };
            setTimeout(() => {
                router.push("/")
            }, 1000);
        } else {
            isLogin.value = true;
            message.value = { text: 'Account created! Please sign in.', isError: false };
        }
    } catch (err: any) {
        message.value = {
            text: err.response?.data?.message || 'Connection Error to Backend',
            isError: true
        };
    } finally {
        loading.value = false;
    }
};
</script>

<template>
    <div class="flex min-h-screen items-center justify-center bg-slate-50 p-4">
        <div class="w-full max-w-md rounded-2xl bg-white p-8 shadow-xl">
            <header class="mb-8 text-center">
                <h1 class="text-3xl font-bold text-slate-800">
                    {{ isLogin ? 'Sign In' : 'Register' }}
                </h1>
                <p class="mt-2 text-slate-500">Contact Management System</p>
            </header>

            <form @submit.prevent="handleSubmit" class="space-y-5">
                <div v-if="message.text"
                    :class="message.isError ? 'bg-red-50 text-red-600' : 'bg-green-50 text-green-600'"
                    class="rounded-lg p-3 text-sm font-medium">
                    {{ message.text }}
                </div>

                <div>
                    <label class="block text-sm font-semibold text-slate-700">Username</label>
                    <input v-model="form.username" type="text" required
                        class="mt-1 w-full rounded-lg border border-slate-300 px-4 py-2 outline-none focus:ring-2 focus:ring-indigo-500" />
                </div>

                <div v-if="!isLogin">
                    <label class="block text-sm font-semibold text-slate-700">Email</label>
                    <input v-model="form.email" type="email" required
                        class="mt-1 w-full rounded-lg border border-slate-300 px-4 py-2 outline-none focus:ring-2 focus:ring-indigo-500" />
                </div>

                <div>
                    <label class="block text-sm font-semibold text-slate-700">Password</label>
                    <input v-model="form.password" type="password" required
                        class="mt-1 w-full rounded-lg border border-slate-300 px-4 py-2 outline-none focus:ring-2 focus:ring-indigo-500" />
                </div>

                <button type="submit" :disabled="loading"
                    class="w-full rounded-lg bg-indigo-600 py-2.5 font-bold text-white transition-all hover:bg-indigo-700 disabled:bg-slate-300">
                    <span v-if="loading">Processing...</span>
                    <span v-else>{{ isLogin ? 'Login' : 'Create Account' }}</span>
                </button>
            </form>

            <footer class="mt-6 text-center text-sm text-slate-600">
                <button @click="isLogin = !isLogin" class="font-bold text-indigo-600 hover:underline">
                    {{ isLogin ? "Don't have an account? Sign Up" : "Already have an account? Sign In" }}
                </button>
            </footer>
        </div>
    </div>
</template>