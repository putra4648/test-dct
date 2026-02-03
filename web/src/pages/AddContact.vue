<script setup lang="ts">
import { onMounted, ref } from 'vue';
import Button from '../components/Button.vue';
import { GetGroups, type Group } from '../api/groups_api';
import { AddContact, type Contact } from '../api/contact_api';
import { router } from '../route';
import api from '../api/api';

const groups = ref<Group[]>([])

const form = ref<Contact>({
  firstname: '',
  lastname: '',
  email: '',
  phone: '',
  groups: []
})


const add = async () => {
  // console.log(form.value)
  const userId = (await api.get("/auth/profile")).data
  form.value.userId = userId
  await AddContact(form.value, router)
}
const getGroups = async () => {
  const result = GetGroups()
  groups.value = await result;
}

const addGroup = () => {
  form.value.groups.push({ id: 0, name: '' })
}

onMounted(() => {
  getGroups()
})

</script>

<template>
  <div class="container mx-auto">
    <Button name="Back" @click="$router.push('/')" />
    <h1 class="text-2xl font-bold">Add Contact</h1>
    <form class="flex flex-col space-y-4">
      <label class="font-medium" for="firstname">Firstname</label>
      <input v-model="form.firstname" class="rounded-sm border w-1/4 shadow-2xl" name="firstname"
        placeholder="firstname" />
      <label class="font-medium" for="lastname">Lastname</label>
      <input v-model="form.lastname" class="rounded-sm border w-1/4 shadow-2xl" name="firstname"
        placeholder="firstname" />
      <label class="font-medium" for="email">Email</label>
      <input v-model="form.email" class="rounded-sm border w-1/4 shadow-2xl" name="firstname" placeholder="email" />
      <label class="font-medium" for="phone">Phone</label>
      <input v-model="form.phone" class="rounded-sm border w-1/4 shadow-2xl" name="firstname" placeholder="phone" />
      <label class="font-medium" for="group">Group</label>
      <Button @click="addGroup" name="Add Group" />
      <select v-for="g in form.groups" v-model="g.id" class="rounded-sm border w-1/4 shadow-2xl">
        <option>Select a group</option>
        <option v-for="g in groups" :value="g.id">{{ g.name }}</option>
      </select>
      <Button type="button" name="submit" @click="add" />
    </form>
  </div>
</template>