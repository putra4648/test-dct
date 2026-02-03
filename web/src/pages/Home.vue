<script setup lang="ts">
import { onMounted, ref } from 'vue';
import Button from '../components/Button.vue';
import { DeleteContact, SearchContacts, type Contact } from '../api/contact_api';
import { useRouter } from 'vue-router';
const router = useRouter()

const contacts = ref<Contact[]>([])
const search = ref('')

const getContacts = async () => {
    const result = await SearchContacts(search.value)
    contacts.value = result;
}

const deleteContact = async (id: number) => {
    await DeleteContact(id)
    await getContacts()
}

const signOut = () => {
    localStorage.clear()
    setTimeout(() => {
        router.push('/auth')
    }, 1000);
}

onMounted(() => {
    getContacts()

})
</script>

<template>
    <div class="container mx-auto">
        <Button @click="signOut" name="Sign Out" />
        <div class="flex flex-col space-y-2">
            <h1 class="text-xl font-bold">Contact Management App</h1>
            <Button @click="$router.push('/add')" name="Add Contact" />
            <Button @click="$router.push('/add-group')" name="Add Group" />
            <form class="flex flex-col space-y-2">
                <input v-model="search" class="border rounded-lg w-1/4" placeholder="Search phone number" />
                <Button @click="getContacts" name="search" />
            </form>
        </div>

        <!-- Contact list -->
        <div class="grid grid-cols-4 gap-4">
            <div v-for="c in contacts" class="border p-2">
                <p class="font-medium">Firstname {{ c.firstname }}</p>
                <p class="font-medium">Lastname {{ c.lastname }}</p>
                <p class="font-medium">Email {{ c.email }}</p>
                <p class="font-medium">Phone {{ c.phone }}</p>
                <p class="font-medium">Group {{c.groups?.map(g => g.name).join(', ')}}</p>
                <Button @click="deleteContact(c.id!)" name="Delete" />
            </div>
        </div>
    </div>
</template>