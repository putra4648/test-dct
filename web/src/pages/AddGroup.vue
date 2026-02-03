<script setup lang="ts">
import { onMounted, ref } from 'vue';
import Button from '../components/Button.vue';
import { RouterLink, useRouter } from 'vue-router';
import { AddGroup, GetGroups, type Group } from '../api/groups_api';

const router = useRouter();

const form = ref<Group>({
    id: 0,
    name: ""
})

const groups = ref<Group[]>([])


const add = function () {
    AddGroup(form.value, router)
}

const getGroups = async () => {
    const result = GetGroups()
    groups.value = await result;
}

onMounted(() => {
    getGroups();
})

</script>

<template>
    <div class="container mx-auto">
        <Button name="Back" @click="$router.push('/')" />
        <h1 class="text-2xl font-bold">Add Groupname</h1>
        <form class="flex flex-col space-y-4">
            <label class="font-medium" for="firstname">Group name</label>
            <input v-model="form.name" class="rounded-sm border w-1/4 shadow-2xl" name="firstname"
                placeholder="firstname" />

            <Button type="button" name="submit" @click="add" />
        </form>

        <hr class="my-4" />

        <div class="grid grid-cols-4">
            <div v-for="g in groups">
                <p class="font-medium">Group ID {{ g.id }}</p>
                <p class="font-medium">Group Name {{ g.name }}</p>
            </div>
        </div>
    </div>
</template>