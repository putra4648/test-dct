import type { Router } from "vue-router";
import type { Group } from "./groups_api";
import type { G } from "vue-router/dist/router-CWoNjPRp.mjs";
import api from "./api";

export async function AddContact(body: Contact, router: Router) {
  await api.post("http://localhost:8080/api/contacts", body).then((res) => {
    setTimeout(() => {
      router.push("/");
    }, 1000);
  });
}

export async function SearchContacts(name: string) {
  const result = await api.get(
    "http://localhost:8080/api/contacts/search?name=" + name,
  );
  return result.data as Contact[];
}

export async function GetContacts() {
  const result = await api.get("http://localhost:8080/api/contacts");
  return result.data as Contact[];
}

export async function DeleteContact(id: number) {
  await api.delete("http://localhost:8080/api/contacts/" + id);
}

export type Contact = {
  id?: number;
  firstname: string;
  lastname: string;
  email: string;
  phone: string;
  groups: Group[];
  userId?: number;
};
